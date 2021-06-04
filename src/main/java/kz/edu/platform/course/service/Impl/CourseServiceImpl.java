package kz.edu.platform.course.service.Impl;

import kz.edu.platform.common.model.UserContext;
import kz.edu.platform.course.model.Course;
import kz.edu.platform.course.model.Lecture;
import kz.edu.platform.course.model.dto.CourseDto;
import kz.edu.platform.course.model.dto.LectureDto;
import kz.edu.platform.course.repositories.CourseRepository;
import kz.edu.platform.course.service.CourseService;
import kz.edu.platform.course.service.LectureService;
import kz.edu.platform.course.util.mapper.CourseMapper;
import kz.edu.platform.course.util.mapper.LectureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LectureService lectureService;

    @Override
    public CourseDto createCourse(CourseDto courseDto) {
        Course course = CourseMapper.from(courseDto);

        List<LectureDto> lectureDtos = courseDto.getLectures();
        if (lectureDtos != null && !lectureDtos.isEmpty()) {
            // Написать логику загрузки attachments
            List<Lecture> lectures = lectureDtos.stream()
                    .map(lectureService::save)
                    .map(LectureMapper::from)
                    .collect(Collectors.toList());
            course.setLectures(lectures);
        }

        return CourseMapper.from(courseRepository.save(course)); // когда так сохряняем автоматом и лекций сохраняются или их нужно отдельно в лектуресервисе сохранять?
    }

    @Override
    public Page<CourseDto> findAll(Pageable pageable, UserContext userContext) {

        

        return CourseMapper.from(courseRepository.findAll(pageable));
    }


    private Course findById(long id) {
        Optional<Course> courseOpt = courseRepository.findById(id);

        if (!courseOpt.isPresent()){
            throw new IllegalArgumentException("There is no course with id: " + id);
        }

        return courseOpt.get();
    }

    @Override
    public CourseDto getCourseById(long id) {
        return CourseMapper.from(findById(id));
    }

    @Override
    public CourseDto update(long id, Course courseDto) {

        Course course = findById(id);

        if (courseDto.getName() != null && !courseDto.getName().isEmpty()){
            course.setName(courseDto.getName());
        }

        if (courseDto.getDescription() != null && !courseDto.getDescription().isEmpty()){
            course.setDescription(courseDto.getDescription());
        }

        if (courseDto.getPublishStatus() != null){
            course.setPublishStatus(courseDto.getPublishStatus());
        }

        // Обновление лекций не стал здесь делать, так как там нужно будет осторожным

        course.setUpdatedAt(new Date());
        course = courseRepository.save(course);

        return CourseMapper.from(course);
    }

    @Override
    public CourseDto addLectureToCourse(long courseId, long lectureId, Integer order) {

        Course course = findById(courseId);
        Lecture lecture = lectureService.findById(lectureId);

//        if (course.getLectures() == null){
//            course.setLectures(new HashMap<>());
//        }
//
//        course.getLectures().put(order, lecture); // расскоментить когда дойдем до этого функионала на фронте

        course.setUpdatedAt(new Date());

        return CourseMapper.from(courseRepository.save(course));
    }

    @Override
    public CourseDto excludeLectureFromCourse(long courseId, long lectureId) {

        Course course = findById(courseId);
        Lecture lecture = lectureService.findById(lectureId);
//
//        if (course.getLectures().containsValue(lecture)){
//            course.getLectures().remove(lecture); // расскоментить когда дойдем до этого функионала на фронте
//        }

        course.setUpdatedAt(new Date());

        return CourseMapper.from(courseRepository.save(course));
    }

    @Override
    public LectureDto getAllLectures(long courseId) { // have to test // вообще нужно ли? так как есть метод findById который возвращает всю инфу о курсе

        Course course = findById(courseId);

        return null;
    }
}
