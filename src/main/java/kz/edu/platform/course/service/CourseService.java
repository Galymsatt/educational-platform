package kz.edu.platform.course.service;

import kz.edu.platform.course.model.Course;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface CourseService {

    Course createCourse(Course course);
    List<Course> findAll();
    Course findById(long id);
    Course update(long id, Course course);

}
