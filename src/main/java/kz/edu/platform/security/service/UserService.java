package kz.edu.platform.security.service;

import kz.edu.platform.common.model.Role;
import kz.edu.platform.common.model.Status;
import kz.edu.platform.common.model.User;
import kz.edu.platform.security.model.ResetPasswordData;
import kz.edu.platform.security.repository.RoleRepositiry;
import kz.edu.platform.security.repository.UserRepository;
import kz.edu.platform.security.security.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

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

    @Value("${client.base.url}")
    private String clientBaseUrl;

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

        Set<Role> roleSet = userDTO.getRoles().stream()
                .map(roleRepositiry::findRoleByName)
                .collect(Collectors.toSet());

        user.setRoles(roleSet.stream().collect(Collectors.toList()));
//        user.setStatus(Status.NOT_ACTIVE);
        user.setStatus(Status.ACTIVE);
        String hashCode = UUID.randomUUID().toString();
        user.setHashCode(hashCode);

//        User registered = null;
        User registered = userRepository.save(user);

        Map<String, Object> resetPsrdTempData = new HashMap<>();
        resetPsrdTempData.put("userFullName", user.getLastName() + " " + user.getFirstName());
        resetPsrdTempData.put("url", clientBaseUrl + "/api/v1/auth/changePassword/" + hashCode);

        Context ctx = new Context();
        ctx.setVariables(resetPsrdTempData);

        String html = generateEmailHtml.generate(ctx);

        mailSenderService.sendMail(user.getEmail(), "Change Password", html);
        return registered;
    }

    public void updatePassword(ResetPasswordData resetPasswordData) {
        User user = userRepository.findByHashCode(resetPasswordData.getToken());

        user.setPassword(passwordEncoder.encode(resetPasswordData.getPassword()));
        userRepository.save(user);
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
