package kz.edu.platform.course.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="t_attachments")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    private String authorId; // uplodaerId, что бы знать какой ученик прикреплял что. "boolean isStudent" убираю, так как можонго будет узнать студент ли это или нет смотря на роля

    private String name;

    private String key;

    private Long size;

    private Date uploadDate;

    // Написать функционал добавления приложений

}
