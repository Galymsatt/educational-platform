package kz.edu.platform.course.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

/*
Здесь будут хранится инфа о тех студентах которые индивидуально приобрели курс
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="t_course_enrollment")
public class CourseEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Если укажем связь я думаю возникнут траблы с созданим таблицы
//    private Course course;
    @OneToOne
    private Course course;

    @OneToOne
    private Student student;

    private Date subscriptionToDate; // Подписка до

}
