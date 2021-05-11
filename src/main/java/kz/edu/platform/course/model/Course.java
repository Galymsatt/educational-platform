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

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private PublishStatus publishStatus;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseId")
    private Map<Integer, Lecture> lectures;

}
