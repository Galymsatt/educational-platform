package kz.edu.platform.course.model.dto;

import kz.edu.platform.common.model.BaseEntity;
import kz.edu.platform.course.model.Lecture;
import kz.edu.platform.course.model.PublishStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto extends BaseEntity {

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private PublishStatus publishStatus;

    private List<LectureDto> lectures;

}
