package kz.edu.platform.course.model;

import kz.edu.platform.common.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Обозначает какие файлы относятся к каким лекциям
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "t_lessons")
public class Lesson extends BaseEntity {

    @OneToOne
    private Lecture lecture; // Еще подумать

    @ManyToMany
    private List<Attachment> attachments; // List комбинациясын карау

}
