package kz.edu.platform.course.service.Impl;

import kz.edu.platform.common.model.Role;
import kz.edu.platform.common.model.User;
import kz.edu.platform.common.model.UserContext;
import kz.edu.platform.course.model.Course;
import kz.edu.platform.course.model.CourseEnrollment;
import kz.edu.platform.course.model.Lesson;
import kz.edu.platform.course.model.dto.CourseEnrollmentDto;
import kz.edu.platform.course.model.dto.LessonDto;
import kz.edu.platform.course.repositories.CourseEnrollmentRepository;
import kz.edu.platform.course.service.CourseEnrollmentService;
import kz.edu.platform.course.util.mapper.CourseEnrollmentMapper;
import kz.edu.platform.course.util.mapper.LectureMapper;
import kz.edu.platform.course.util.mapper.LessonMapper;
import kz.edu.platform.security.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseEnrollmentServiceImpl implements CourseEnrollmentService {

    private final CourseEnrollmentRepository repository;
    private final CourseServiceImpl courseService; // нужно интерфейс инджектить или сервис класс? инджектнул класс так как нужен был метод findById
    private final UserServiceImpl userService; // нужно интерфейс инджектить или сервис класс? инджектнул класс так как нужен был метод findById
    private final LessonServiceImpl lessonService; // нужно интерфейс инджектить или сервис класс? инджектнул класс так как нужен был метод findById

    @Value("${role-name.admin}")
    private String ADMIN_ROLE;
    @Value("${role-name.teacher}")
    private String TEACHER_ROLE;
    @Value("${role-name.student}")
    private String STUDENT_ROLE;

    @Override
    public CourseEnrollmentDto save(CourseEnrollmentDto courseEnrollmentDto) {
        CourseEnrollment courseEnrollment = CourseEnrollmentMapper.from(courseEnrollmentDto);

        Course course = courseService.findById(courseEnrollmentDto.getCourse().getId());
        courseEnrollment.setCourse(course); // нужно полный курс сетать или достаточно курс с одим лишь полем id сетать?

        User teacher = userService.findById(courseEnrollmentDto.getTeacher().getId());
        courseEnrollment.setTeacher(teacher); // нужно полный курс сетать или достаточно курс с одим лишь полем id сетать?

        List<User> students = courseEnrollmentDto.getStudents().stream()
                .map(st -> userService.findById(st.getId()))
                .collect(Collectors.toList());
        courseEnrollment.setStudents(students); // нужно полный курс сетать или достаточно курс с одим лишь полем id сетать?

        List<Lesson> lessons = course.getLectures().stream()
                .map(lctr -> LessonMapper.from(
                        lessonService.save(LessonDto.builder()
                                .lecture(LectureMapper.from(lctr))
                                .build())))
                .collect(Collectors.toList());
        courseEnrollment.setLessons(lessons);

        courseEnrollment = repository.save(courseEnrollment);

        return CourseEnrollmentMapper.from(courseEnrollment);
    }

    @Override
    public CourseEnrollmentDto update(long id, CourseEnrollmentDto dto) {
        CourseEnrollment courseEnrollment = findById(dto.getId());

        if (dto.getEnrollmentType() != null) {
            courseEnrollment.setEnrollmentType(dto.getEnrollmentType());
        }
        if (StringUtils.isNotBlank(dto.getName())) {
            courseEnrollment.setName(dto.getName());
        }
        if (StringUtils.isNotBlank(dto.getDescription())) {
            courseEnrollment.setName(dto.getDescription());
        }

        // нужно дополнить для остальных полей

        courseEnrollment.setUpdatedAt(new Date());
        courseEnrollment = repository.save(courseEnrollment);

        return CourseEnrollmentMapper.from(courseEnrollment);
    }

    @Override
    public Page<CourseEnrollmentDto> findAll(Pageable pageable, UserContext userContext) {
        boolean isAdmin = false;
        boolean isTeacher = false;
        boolean isStudent = false;

        for (String role : userContext.getRoles()) {
            if (role.equals(ADMIN_ROLE)) {
                isAdmin = true;
                break;
            } else if (role.equals(TEACHER_ROLE)) {
                isTeacher = true;
                break;
            } else if (role.equals(STUDENT_ROLE)) {
                isStudent = true;
                break;
            }
        }

        User user = userService.findByUsername(userContext.getUsername());
        if (isAdmin) {
            return CourseEnrollmentMapper.from(repository.findAll(pageable));
        } else if (isTeacher) {
            return CourseEnrollmentMapper.from(repository.findAllByTeacher(pageable, user));
        } else if (isStudent) {
            return CourseEnrollmentMapper.from(repository.findAllByStudentsContains(pageable, user));
        } else {
            throw new IllegalArgumentException("User with username: " + userContext.getUsername() + " doesn't have any role");
        }

    }

    @Override
    public List<CourseEnrollmentDto> findAll(UserContext userContext) {
        boolean isAdmin = false;
        boolean isTeacher = false;
        boolean isStudent = false;

        for (String role : userContext.getRoles()) {
            if (role.equals(ADMIN_ROLE)) {
                isAdmin = true;
                break;
            } else if (role.equals(TEACHER_ROLE)) {
                isTeacher = true;
                break;
            } else if (role.equals(STUDENT_ROLE)) {
                isStudent = true;
                break;
            }
        }

        User user = userService.findByUsername(userContext.getUsername());
        if (isAdmin) {
            return CourseEnrollmentMapper.from(repository.findAll());
        } else if (isTeacher) {
            return CourseEnrollmentMapper.from(repository.findAllByTeacher(user));
        } else if (isStudent) {
            return CourseEnrollmentMapper.from(repository.findAllByStudentsContains(user));
        } else {
            throw new IllegalArgumentException("User with username: " + userContext.getUsername() + " doesn't have any role");
        }
    }

    @Override
    public CourseEnrollmentDto getCourseEnrollmentById(long id) {
        return CourseEnrollmentMapper.from(findById(id));
    }

    @Override
    public CourseEnrollmentDto addStudentToCourseEnrollment(long courseEnrollmentId, long stuId) {
        CourseEnrollment courseEnrollment = findById(courseEnrollmentId);

        User student = userService.findById(stuId);
        courseEnrollment.getStudents().add(student);

        courseEnrollment.setUpdatedAt(new Date());
        courseEnrollment = repository.save(courseEnrollment);

        return CourseEnrollmentMapper.from(courseEnrollment);
    }

    private CourseEnrollment findById(long id) {
        Optional<CourseEnrollment> courseEnrollmentOpt = repository.findById(id);

        if (!courseEnrollmentOpt.isPresent()){
            throw new IllegalArgumentException("There is no CourseEnrollment with id: " + id);
        }

        return courseEnrollmentOpt.get();
    }

}
