package kz.edu.platform.course.model;

import kz.edu.platform.common.model.BaseEntity;
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
public class Group extends BaseEntity {

    private String name;
    private String description;

//    @OneToMany
//    private Course course; // Обдумать связь
//    @ManyToOne
//    private Course course; // посмотреть в других проектах как группа и лекций связаны

    /**
     * // map?. Тут поле тольк для attachments, может сделать как в классе CourseEnrollment0 поле Map<Integer, Attachment> attachments;// А что будет с мапой с дубелм ключей(Integer lectureId)
     *
     * когда вытаскиваем attachments, студенту с сервера возвращаем только его файлы и файлы тичера, если все возвратить памяти будет много жрать.
     * Хотя если так подумать это всего лишь стрингове поле где храниться ссылка на файловое хранилище, в целях безопасности возвращаем файлы только текущего студента
     * а тичеру возвращаем все
     *
     * Если все прикрепляемые файлы будут хранится в файловом хранилище, то что будет с памятью?, эффективно ли хранить в файловом хранилище?, где еще можно хранить?
     */
    private List<Lesson> lessons; // Тут поле тольк для attachments, может сделать как в классе CourseEnrollment0 поле Map<Integer, Attachment> attachments;// А что будет с мапой с дубелм ключей(Integer lectureId). Получается нужно возвратить private Course course;

    @ManyToOne
    private Teacher teacher; // Обдумать связь

    @OneToMany // Many to many болу керек, может быть так что студент прошел java se и следом переходит к группе java ee
    private List<Student> students;

    // поле куда будут выкладываться материалы индивидуальные матреиалы для каждой группы. Но это поле должно быть не здесь а в сущности lecture, вот такой кейс нужно решить

}
