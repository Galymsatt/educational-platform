package kz.eduplatform.courseadapter.config.security.dto;

import kz.eduplatform.courseadapter.model.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Bean;

@Data
public class UserDTO {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public static User toEntity(UserDTO userDTO) {
        return User.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .password(userDTO.getPassword())
                .build();
    }

}
