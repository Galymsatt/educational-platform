package kz.edu.platform.common.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class User0 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id; // Почему поля протектед, потому что пользователи(админ, учитель, студент) будут наследоваться от этого класса

    protected String username;

    protected String password;
}
