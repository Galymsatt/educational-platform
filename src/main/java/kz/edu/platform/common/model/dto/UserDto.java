package kz.edu.platform.common.model.dto;

import kz.edu.platform.common.model.BaseEntity;
import kz.edu.platform.common.model.User;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
public class UserDto extends BaseEntity {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> roles; // role names

}
