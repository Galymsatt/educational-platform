package kz.edu.platform.course.model;

import kz.edu.platform.common.model.BaseEntity;
import kz.edu.platform.common.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Сущность подразумевает общее группы и индивидуальног
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="t_course_enrollment")
public class CourseEnrollment extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private EnrollmentType enrollmentType;

    private String name;

    private String description;

    /**
     * Поле добавлялось из за инидивидаульных, могу убрать так как решил что леций будут доступны через поле lessons
     * И так как индивидуальные не будут курироваться тичерами поле lessons в сервисе сохранения все лекций будут автоматом инициализированы с класса Course
     *
     * Сейчас решил оставить как признак проходимого курса CourseEnrollment
     */
    @ManyToOne
    private Course course;

    /**
     * Доступ открыт до
     */
    private Date subscriptionToDate;

    /**
     * Если индивидуально проходит курс то скорее не будет тичера
     */
    @ManyToOne
    private User teacher;

    /**
     * Many to many болу керек, может быть так что студент прошел java se и следом переходит к группе java ee
     * Получается если нидивидуальный то в листе будет только один ученик
     */
    @ManyToMany
    private List<User> students;

    /**
     * Прикрепляемые файлы
     */
    @OneToMany
    private List<Lesson> lessons;
//    @JoinColumn(name = "lesson_id")
//    private Lesson lessons;

}
