package kz.edu.platform.course.model;

import kz.edu.platform.common.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * Здесь будут хранится инфа о тех студентах которые индивидуально проходят курс
 */

//@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
//@Table(name="t_course_enrollment0")
public class CourseEnrollment0 extends BaseEntity { // может объедянить с классом Group, поле subscriptionToDate может и у группы быть. Объеденяю, удалить

    private String description;

    // Если укажем связь я думаю возникнут траблы с созданим таблицы
//    private Course course;
    @OneToOne
    private Course course;

    @OneToOne
    private Student student;

    private Date subscriptionToDate; // Подписка до

    /**
     * Map<lectureId, List<Attachment>>. Прикрепляемые файлы индивидуальных учеников
     */
//    private Map<Integer, List<Attachment>> attachments;
    private List<Lesson> lessons; // Прикрепляемые файлы индивидуальных учеников


    public class LectureAttachment extends BaseEntity{ // класс такой же что и Lesson, удалить

        private long lectureId; // Если курс будет одним то и лекций будут одини и теми же, так не пойдет. Но так как LectureAttachment.id разными будет понятно какой LectureAttachment относится к какому юзеру
        private List<Attachment> attachments;

    }

}
