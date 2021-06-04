package kz.edu.platform.security.service;

import kz.edu.platform.common.model.Role;
import kz.edu.platform.common.model.Status;
import kz.edu.platform.common.model.User;
import kz.edu.platform.security.repository.RoleRepositiry;
import kz.edu.platform.security.repository.UserRepository;
import kz.edu.platform.security.security.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

//@Lazy(false)
//@Component
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepositiry roleRepositiry;
    private final BCryptPasswordEncoder passwordEncoder;
//    private final PasswordEncoder passwordEncoder;
    private final MailSenderService mailSenderService;
    private final GenerateEmailService generateEmailHtml;

    public String generateString() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

//    @PostConstruct
    public User register(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()) != null) {
            throw new IllegalArgumentException("Username " + userDTO.getUsername() + " already exists");
        }

        User user = UserDTO.toEntity(userDTO);
        Role role = roleRepositiry.findRoleByName("USER");
        String hashCode = UUID.randomUUID().toString();
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(role);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println("Encoded password: " + user.getPassword());
        user.setRoles(userRoles);
//        user.setStatus(Status.NOT_ACTIVE);
        user.setStatus(Status.ACTIVE);
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
