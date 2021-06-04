package kz.edu.platform.security.security.dto;

import kz.edu.platform.common.model.Role;
import kz.edu.platform.common.model.User;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> roles; // role names

    public static User toEntity(UserDTO userDTO) {
        return User.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .build();
    }

}
