package kz.eduplatform.courseadapter.config.security.controller;

import kz.eduplatform.courseadapter.config.security.dto.AuthDTO;
import kz.eduplatform.courseadapter.config.security.dto.UserDTO;
import kz.eduplatform.courseadapter.config.security.jwt.JwtTokenProvider;
import kz.eduplatform.courseadapter.model.User;
import kz.eduplatform.courseadapter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.register(userDTO));
    }
//
//    @GetMapping("/password")
//    public ResponseEntity firstPassword(@PathVariable )


}
