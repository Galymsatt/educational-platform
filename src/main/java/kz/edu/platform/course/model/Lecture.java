package kz.edu.platform.course.model;

import kz.edu.platform.common.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="t_lectures")
public class Lecture extends BaseEntity {

//    @Id // убрал из за того что сейчас будут наследоваться с BaseEntity
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;

//    Если указать и отсюда связь то нужно указать кто главный класс
////    @ManyToOne
////    private Course course;

    private String name;

    private String description;

    private String content;

    //Также поле для загружаемых файлов(Архив с проектом, java файл)

    // Поле для хранения ссылки на видео. Сейчас думаю о ютуб, но интересует момент не будут ли студенты просто копировать ссылки с видео и пересылать
}
