package kz.edu.platform.security.service.impl;

import kz.edu.platform.common.model.Role;
import kz.edu.platform.common.model.Status;
import kz.edu.platform.common.model.User;
import kz.edu.platform.security.model.ResetPasswordData;
import kz.edu.platform.security.repository.RoleRepositiry;
import kz.edu.platform.security.repository.UserRepository;
import kz.edu.platform.common.model.dto.UserDto;
import kz.edu.platform.security.service.GenerateEmailService;
import kz.edu.platform.security.service.MailSenderService;
import kz.edu.platform.security.service.UserService;
import kz.edu.platform.security.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepositiry roleRepositiry;
    private final BCryptPasswordEncoder passwordEncoder;
//    private final PasswordEncoder passwordEncoder;
    private final MailSenderService mailSenderService;
    private final GenerateEmailService generateEmailHtml;

    @Value("${client.base.url}")
    private String clientBaseUrl;

    private final String ADMIN_ROLE = "ADMIN";
    private final String TEACHER_ROLE = "TEACHER";
    private final String STUDENT_ROLE = "STUDENT";

    public String generateString() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

    @Override
    public UserDto save(UserDto userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()) != null) {
            throw new IllegalArgumentException("Username " + userDTO.getUsername() + " already exists");
        }
        User user = UserMapper.from(userDTO);

        if (userDTO.getRoles().isEmpty()) {
            throw new IllegalArgumentException("Roles are empty, specify the roles");
        }
        Set<Role> roleSet = userDTO.getRoles().stream()
                .map(roleRepositiry::findRoleByName)
                .collect(Collectors.toSet());

        user.setRoles(roleSet.stream().collect(Collectors.toList()));
//        user.setStatus(Status.NOT_ACTIVE); // Можно доработать так что бы в начале было не актив, после того как пароль задастся станет активным
        user.setStatus(Status.ACTIVE);
        String hashCode = UUID.randomUUID().toString();
        user.setHashCode(hashCode);

        User savedUser = userRepository.save(user);

        Map<String, Object> resetPsrdTempData = new HashMap<>();
        resetPsrdTempData.put("userFullName", user.getLastName() + " " + user.getFirstName());
        resetPsrdTempData.put("url", clientBaseUrl + "/api/v1/auth/changePassword/" + hashCode);

        Context ctx = new Context();
        ctx.setVariables(resetPsrdTempData);

        String html = generateEmailHtml.generate(ctx);

        mailSenderService.sendMail(user.getEmail(), "Change Password", html);

        return UserMapper.from(savedUser);
    }

    public void updatePassword(ResetPasswordData resetPasswordData) { // почему если прописываю в сервисе требует ретурн тип?
        User user = userRepository.findByHashCode(resetPasswordData.getToken());

        user.setPassword(passwordEncoder.encode(resetPasswordData.getPassword()));
        userRepository.save(user);
    }

    //////////////

    @Override
    public Page<UserDto> getAllUsers(Pageable pageable, boolean admin, boolean teacher, boolean student) {

        List<Role> roles = new ArrayList<>();
        if (admin) {
            roles.add(roleRepositiry.findRoleByName(ADMIN_ROLE));
        }
        if (teacher) {
            roles.add(roleRepositiry.findRoleByName(TEACHER_ROLE));
        }
        if (student) {
            roles.add(roleRepositiry.findRoleByName(STUDENT_ROLE));
        }

//        return UserMapper.from(userRepository.findAll(pageable));
        return UserMapper.from(userRepository.findAllByRolesIn(pageable, roles));
    }

    @Override
    public UserDto getByUsername(String username) {
        User user = findByUsername(username);

        return UserMapper.from(user);
    }

    @Override
    public UserDto getById(long id) {
        User user = findById(id);

        return UserMapper.from(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findById(Long id) {
        Optional<User> userOpt = userRepository.findById(id);

        if (!userOpt.isPresent()){
            throw new IllegalArgumentException("There is no user with id: " + id);
        }

        return userOpt.get();
    }

    public void delete(Long id) { // логику скорее нужно переписать(не удалять юзеров а поменять статус на неактивный)
        userRepository.deleteById(id);
    }
}
