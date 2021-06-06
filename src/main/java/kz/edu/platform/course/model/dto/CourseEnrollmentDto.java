package kz.edu.platform.course.model.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import kz.edu.platform.common.model.BaseEntity;
import kz.edu.platform.common.model.User;
import kz.edu.platform.course.model.EnrollmentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CourseEnrollmentDto extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private EnrollmentType enrollmentType;

    private String name;

    private String description;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private CourseDto course;

    private Date subscriptionToDate;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private User teacher;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<User> students;

    private List<LessonDto> lessons;

}