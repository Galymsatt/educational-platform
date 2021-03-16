package kz.edu.platform.course.service;

import kz.edu.platform.course.model.Course;
import kz.edu.platform.course.model.Lecture;

import java.util.List;

public interface CourseService {

    Course createCourse(Course course);
    List<Course> findAll();
    Course findById(long id);
    Course update(long id, Course course);
    Course addLectureToCourse(long courseId, long lectureId, Integer order);
    Course excludeLectureFromCourse(long courseId, long lectureId);
    Lecture getAllLectures(long courseId);

}
