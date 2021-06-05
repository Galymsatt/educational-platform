package kz.edu.platform.course.service;

import kz.edu.platform.common.model.UserContext;
import kz.edu.platform.course.model.dto.CourseEnrollmentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseEnrollmentService {

    CourseEnrollmentDto save(CourseEnrollmentDto courseEnrollmentDto);
    Page<CourseEnrollmentDto> findAll(Pageable pageable, UserContext userContext);
    CourseEnrollmentDto getCourseEnrollmentById(long id);
    CourseEnrollmentDto update(long id, CourseEnrollmentDto courseEnrollmentDto);

}
