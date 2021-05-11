package kz.edu.platform.course.model;

import kz.edu.platform.common.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="t_courses")
public class Course extends BaseEntity {

//    @Id // убрал из за того что сейчас будут наследоваться с BaseEntity
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private PublishStatus publishStatus;

//    private Integer teacher; // почему тип не Teacher // Удираю поле, причина в классе Group

//    @OneToMany(cascade = CascadeType.ALL)
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseId")
    private List<Lecture> lectures;

}
