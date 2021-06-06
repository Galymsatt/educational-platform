package kz.edu.platform.security.security.contextResolver;

import kz.edu.platform.common.model.User;
import kz.edu.platform.common.model.UserContext;
import kz.edu.platform.security.security.jwt.JwtUser;
import kz.edu.platform.security.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.security.Principal;
import java.util.stream.Collectors;

@Slf4j
public class UserContextArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserServiceImpl userServiceImpl;

    public UserContextArgumentResolver(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(UserContext.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        try {
            Principal userPrincipal = nativeWebRequest.getUserPrincipal();
            log.info("Resolving argument 'UserContext'. Principal {}", userPrincipal);

//            if (userPrincipal instanceof OAuth2Authentication) { // JwtUser нужно экстендиться от OAuth2Authentication, потпом станут доступны методы getPrincipal() и тп, также для обновления токена
//                OAuth2Authentication authentication = (OAuth2Authentication) userPrincipal;
//
//                String username = (String) authentication.getPrincipal();
//                User user = userService.findByUsername(username);
//
//
//                return UserContext.builder()
//                        .username(user.getUsername())
//                        .build();
//            }

            if (userPrincipal instanceof UsernamePasswordAuthenticationToken) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) userPrincipal;
                JwtUser jwtUser = (JwtUser) usernamePasswordAuthenticationToken.getPrincipal();

                String username = jwtUser.getUsername();
                User user = userServiceImpl.findByUsername(username);


                return UserContext.builder()
                        .username(user.getUsername())
                        .roles(user.getRoles().stream()
                                .map(role -> role.getName())
                                .collect(Collectors.toList()))
                        .build();
            }
        } catch (Exception e) {
            log.error(e.toString());
            log.error("Error while resolving argument 'UserContext'. User principal: {}",
                    nativeWebRequest.getUserPrincipal() != null ? nativeWebRequest.getUserPrincipal() : "null");
        }

        return null;
    }
}
