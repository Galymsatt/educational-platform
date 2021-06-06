package kz.edu.platform.course.model;

import kz.edu.platform.common.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

/**
 * Обозначает какие файлы относятся к каким лекциям
 */

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_lessons")
public class Lesson extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture; // Еще подумать // Можно было оставить lectureId

    @ManyToMany
    private List<Attachment> attachments; // List комбинациясын карау

}
