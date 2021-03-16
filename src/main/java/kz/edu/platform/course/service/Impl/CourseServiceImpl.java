package kz.edu.platform.course.service.Impl;

import kz.edu.platform.course.model.Course;
import kz.edu.platform.course.model.Lecture;
import kz.edu.platform.course.repositories.CourseRepository;
import kz.edu.platform.course.service.CourseService;
import kz.edu.platform.course.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LectureService lectureService;

    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Course findById(long id) {
//        return courseRepository.findById(id);
        Optional<Course> courseOpt = courseRepository.findById(id);

        if (!courseOpt.isPresent()){
            throw new IllegalArgumentException("There is no course with id: " + id);
        }

        return courseOpt.get();
    }

    @Override
    public Course update(long id, Course courseDto) {

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

        return course;
    }

    @Override
    public Course addLectureToCourse(long courseId, long lectureId, Integer order) {

        Course course = findById(courseId);
        Lecture lecture = lectureService.findById(lectureId);

        if (course.getLectures() == null){
            course.setLectures(new HashMap<>());
        }

        course.getLectures().put(order, lecture);

        course.setUpdatedAt(new Date());

        return courseRepository.save(course);
    }

    @Override
    public Course excludeLectureFromCourse(long courseId, long lectureId) {

        Course course = findById(courseId);
        Lecture lecture = lectureService.findById(lectureId);

        if (course.getLectures().containsValue(lecture)){
            course.getLectures().remove(lecture);
        }

        course.setUpdatedAt(new Date());

        return courseRepository.save(course);
    }

    @Override
    public Lecture getAllLectures(long courseId) { // have to test // вообще нужно ли? так как есть метод findById который возвращает всю инфу о курсе

        Course course = findById(courseId);

        return null;
    }
}
