package kz.edu.platform.course.service;

import kz.edu.platform.common.model.UserContext;
import kz.edu.platform.course.model.Course;
import kz.edu.platform.course.model.Lecture;
import kz.edu.platform.course.model.dto.CourseDto;
import kz.edu.platform.course.model.dto.LectureDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface CourseService {

    CourseDto createCourse(CourseDto course);
    Page<CourseDto> findAll(Pageable pageable, UserContext userContext);
    List<CourseDto> findAll(UserContext userContext);
    CourseDto getCourseById(long id);
    CourseDto update(long id, Course course);
    CourseDto addLectureToCourse(long courseId, long lectureId, Integer order);
    CourseDto excludeLectureFromCourse(long courseId, long lectureId);
    LectureDto getAllLectures(long courseId); // Должно находиться в LectureService?

}
