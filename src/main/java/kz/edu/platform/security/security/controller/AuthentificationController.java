package kz.edu.platform.security.security.controller;

import kz.edu.platform.common.model.Role;
import kz.edu.platform.common.model.User;
import kz.edu.platform.security.model.ResetPasswordData;
import kz.edu.platform.common.model.dto.UserDto;
import kz.edu.platform.security.model.dto.AuthDto;
import kz.edu.platform.security.security.jwt.JwtTokenProvider;
import kz.edu.platform.security.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth")
public class AuthentificationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserServiceImpl userServiceImpl;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthDto authDTO) {
        try {
            String username = authDTO.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, authDTO.getPassword()));
            User user = userServiceImpl.findByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username " + username + " not found");
            }
            String token = jwtTokenProvider.createToken(username, user.getRoles());

            Map<Object, Object> response = new HashMap<>();

            response.put("username", username);
            response.put("token", token);
            response.put("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList())); // потом убрать, роли не нужно скидывать, в токене зашиты

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username and password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity signUp(@RequestBody UserDto userDTO) {
        return ResponseEntity.ok(userServiceImpl.save(userDTO));
    }

    @PostMapping("/resetPassword")
    public ResponseEntity resetPassword(@RequestBody ResetPasswordData resetPasswordData) {
        userServiceImpl.updatePassword(resetPasswordData);
        return new ResponseEntity(HttpStatus.OK);
    }

}
