package kz.edu.platform.course.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="t_courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private PublishStatus publishStatus;

//    private Integer teacher; // почему тип не Teacher // Удираю поле, причина в классе Group

    @OneToMany(cascade = CascadeType.ALL)
    private List<Lecture> lectures;

}
