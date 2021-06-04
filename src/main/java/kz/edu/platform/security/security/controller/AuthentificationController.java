package kz.edu.platform.security.security.controller;

import kz.edu.platform.common.model.User;
import kz.edu.platform.security.model.ResetPasswordData;
import kz.edu.platform.security.security.dto.AuthDTO;
import kz.edu.platform.security.security.dto.UserDTO;
import kz.edu.platform.security.security.jwt.JwtTokenProvider;
import kz.edu.platform.security.service.UserService;
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

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth")
public class AuthentificationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthDTO authDTO) {
        try {
            String username = authDTO.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, authDTO.getPassword()));
            User user = userService.findByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username " + username + " not found");
            }
            String token = jwtTokenProvider.createToken(username, user.getRoles());

            Map<Object, Object> response = new HashMap<>();

            response.put("username", username);
            response.put("token", token);

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username and password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity signUp(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.register(userDTO));
    }

    @PostMapping("/resetPassword")
    public ResponseEntity resetPassword(@RequestBody ResetPasswordData resetPasswordData) {
        userService.updatePassword(resetPasswordData);
        return new ResponseEntity(HttpStatus.OK);
    }

}
