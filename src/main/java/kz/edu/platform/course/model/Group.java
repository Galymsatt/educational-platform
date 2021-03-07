package kz.edu.platform.course.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/*
Так как будет также курсы которые будут индивидуально проходится, в этом случае нет необхадимости группы
Этот момент тоже нужно просчитать, с точки зрения связей сущностей чтобы максимально было эффективно
Т.е. кто то просто купит подписку на курс
*/

/*
Группа подразумевается 1 группа по обному курсу где будет 1 препод и n учеников

06.03.21 Сейчас думаю что будет курс, учителя и ученики. И все они будут объяденены здесь
Думаю нет смысла связывать к курсу учителя, так как учителей по данному курсу могут быть не один
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="t_groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;

//    @OneToMany
//    private Course course; // Обдумать связь
    @ManyToOne
    private Course course;

    @ManyToOne
    private Teacher teacher; // Обдумать связь

    @OneToMany
    private List<Student> students;

    // поле куда будут выкладываться материалы индивидуальные матреиалы для каждой группы. Но это поле должно быть не здесь а в сущности lecture, вот такой кейс нужно решить

}
