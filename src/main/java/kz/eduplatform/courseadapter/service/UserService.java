package kz.eduplatform.courseadapter.service;

import kz.eduplatform.courseadapter.config.security.dto.UserDTO;
import kz.eduplatform.courseadapter.model.Role;
import kz.eduplatform.courseadapter.model.Status;
import kz.eduplatform.courseadapter.model.User;
import kz.eduplatform.courseadapter.repository.RoleRepositiry;
import kz.eduplatform.courseadapter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Lazy(false)
@Component
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepositiry roleRepositiry;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MailSenderService mailSenderService;
    private final GenerateEmailService generateEmailHtml;

    public String generateString() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

    @PostConstruct
    public User register(UserDTO userDTO) {
        User user = UserDTO.toEntity(userDTO);
        Role role = roleRepositiry.findRoleByName("USER");
        String hashCode = UUID.randomUUID().toString();
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(role);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.NOT_ACTIVE);
        user.setHashCode(hashCode);

        User registered = userRepository.save(user);

        String html = generateEmailHtml.generate(hashCode);

        mailSenderService.sendMail(user.getEmail(), "Change Password", html);
        return registered;
    }

    public List<User> getAllUser() {
        List<User> userList = userRepository.findAll();
        return userList;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return user;
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
