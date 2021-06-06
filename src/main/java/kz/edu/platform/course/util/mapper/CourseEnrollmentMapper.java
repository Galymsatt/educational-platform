package kz.edu.platform.course.util.mapper;

import kz.edu.platform.common.model.User;
import kz.edu.platform.course.model.CourseEnrollment;
import kz.edu.platform.course.model.dto.CourseDto;
import kz.edu.platform.course.model.dto.CourseEnrollmentDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class CourseEnrollmentMapper {
    private static final ModelMapper mapper = Mapper.newMapper();

    public static CourseEnrollmentDto from(CourseEnrollment courseEnrollment) {
//        CourseEnrollmentDto courseEnrollmentDto = mapper.map(courseEnrollment, CourseEnrollmentDto.class);
        CourseEnrollmentDto courseEnrollmentDto = CourseEnrollmentDto.builder()
                // parent fields, need or no need?
                .id(courseEnrollment.getId())
                .createdAt(courseEnrollment.getCreatedAt())
                .updatedAt(courseEnrollment.getUpdatedAt())
                .deletedAt(courseEnrollment.getDeletedAt())
                .status(courseEnrollment.getStatus())
                //
                .enrollmentType(courseEnrollment.getEnrollmentType())
                .name(courseEnrollment.getName())
                .description(courseEnrollment.getDescription())
                .course(CourseDto.builder()
                        .id(courseEnrollment.getCourse().getId())
                        .name(courseEnrollment.getCourse().getName())
                        .build())
                .subscriptionToDate(courseEnrollment.getSubscriptionToDate())
                .teacher(courseEnrollment.getTeacher() != null
                        ? User.builder()
                                .id(courseEnrollment.getTeacher().getId())
                                .username(courseEnrollment.getTeacher().getUsername())
                                .build()
                        : null)
                .students(courseEnrollment.getStudents() != null
                        ? courseEnrollment.getStudents().stream()
                        .map(st -> User.builder()
                                .id(st.getId())
                                .username(st.getUsername())
                                .build())
                        .collect(Collectors.toList())
                        : null)
                .lessons(courseEnrollment.getLessons() != null
                        ? courseEnrollment.getLessons().stream()
                        .map(LessonMapper::from)
                        .collect(Collectors.toList())
                        : null)
                .build();

        return courseEnrollmentDto;
    }

    public static Page<CourseEnrollmentDto> from(Page<CourseEnrollment> page) {
        return page.map(CourseEnrollmentMapper::from);
    }

    public static List<CourseEnrollmentDto> from(List<CourseEnrollment> page) {
        return page.stream().map(CourseEnrollmentMapper::from).collect(Collectors.toList());
    }

    public static CourseEnrollment from(CourseEnrollmentDto courseEnrollmentDto) {
        CourseEnrollment courseEnrollment = mapper.map(courseEnrollmentDto, CourseEnrollment.class);

        return courseEnrollment;
    }
}
