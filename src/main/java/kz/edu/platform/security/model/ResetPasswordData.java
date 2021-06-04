package kz.edu.platform.security.model;

import lombok.Data;

@Data
public class ResetPasswordData {

    private String email; // желательно юзернейм для теста, для бойа норм
    private String token; // in oue case hashCode of User
    private String password;
    private String repeatPassword;

}
