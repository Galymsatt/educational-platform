package kz.edu.platform.course.service.Impl;

import kz.edu.platform.common.model.UserContext;
import kz.edu.platform.course.model.CourseEnrollment;
import kz.edu.platform.course.model.dto.CourseEnrollmentDto;
import kz.edu.platform.course.service.CourseEnrollmentService;
import kz.edu.platform.course.util.mapper.CourseEnrollmentMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CourseEnrollmentServiceImpl implements CourseEnrollmentService {

    @Override
    public CourseEnrollmentDto save(CourseEnrollmentDto courseEnrollmentDto) {
        CourseEnrollment courseEnrollment = CourseEnrollmentMapper.from(courseEnrollmentDto);



        return null;
    }

    @Override
    public Page<CourseEnrollmentDto> findAll(Pageable pageable, UserContext userContext) {
        return null;
    }

    @Override
    public CourseEnrollmentDto getCourseEnrollmentById(long id) {
        return null;
    }

    @Override
    public CourseEnrollmentDto update(long id, CourseEnrollmentDto courseEnrollmentDto) {
        return null;
    }
}
