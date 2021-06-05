package kz.edu.platform.course.model.dto;

import kz.edu.platform.common.model.BaseEntity;
import kz.edu.platform.course.model.Attachment;
import kz.edu.platform.course.model.Lecture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class LessonDto extends BaseEntity {

    private LectureDto lecture;

    private List<Attachment> attachments;

}
