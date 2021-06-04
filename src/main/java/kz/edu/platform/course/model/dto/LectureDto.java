package kz.edu.platform.course.model.dto;

import kz.edu.platform.common.model.BaseEntity;
import kz.edu.platform.course.model.Attachment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToMany;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LectureDto extends BaseEntity {

    private int num;

    private String name;

    private String description;

    private String content;

    private List<Attachment> attachments;

    private String videoLink;

}
