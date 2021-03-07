package kz.edu.platform.course.service.Impl;

import kz.edu.platform.course.model.Course;
import kz.edu.platform.course.repositories.CourseRepository;
import kz.edu.platform.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

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
    public Course update(long id, Course course) {
        return null;
    }
}
