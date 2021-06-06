package kz.edu.platform.common.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserContext {

    private String username;
    private List<String> roles;

}
