package kz.edu.platform.course.model.dto;

import kz.edu.platform.common.model.BaseEntity;
import kz.edu.platform.common.model.Status;
import kz.edu.platform.course.model.PublishStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Data
//@Builder
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto extends BaseEntity {

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private PublishStatus publishStatus;

    private List<LectureDto> lectures;

}
