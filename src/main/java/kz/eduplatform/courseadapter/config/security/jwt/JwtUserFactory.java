package kz.eduplatform.courseadapter.config.security.jwt;

import kz.eduplatform.courseadapter.model.Role;
import kz.eduplatform.courseadapter.model.Status;
import kz.eduplatform.courseadapter.model.User;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class JwtUserFactory {
    public JwtUserFactory() {

    }

    public static JwtUser create(User user) {
        return new JwtUser(user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(), user.getEmail(),
                user.getPassword(),
                user.getStatus().equals(Status.ACTIVE),
                user.getUpdated(),null);
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles){
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
