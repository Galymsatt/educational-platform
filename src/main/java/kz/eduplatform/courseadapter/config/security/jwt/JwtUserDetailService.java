package kz.eduplatform.courseadapter.config.security.jwt;

import kz.eduplatform.courseadapter.model.User;
import kz.eduplatform.courseadapter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User is not found");
        }
        JwtUser jwtUser = JwtUserFactory.create(user);

        return jwtUser;
    }
}
