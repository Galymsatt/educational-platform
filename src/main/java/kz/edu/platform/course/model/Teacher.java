package kz.edu.platform.course.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//Еще под вопросом нужно ли, так как мб думаем что будет только юзеры и их роля
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="t_teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    /**
     * Обдумать как тичер будет видеть своих групп
     * нужно ли добавлять поле?
     */
}
