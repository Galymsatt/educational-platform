package kz.edu.platform.security.security.contextResolver;

import kz.edu.platform.common.model.User;
import kz.edu.platform.common.model.UserContext;
import kz.edu.platform.security.security.jwt.JwtUser;
import kz.edu.platform.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.security.Principal;

@Slf4j
public class UserContextArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;

    public UserContextArgumentResolver(UserService userService) {
        this.userService = userService;
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
                User user = userService.findByUsername(username);


                return UserContext.builder()
                        .username(user.getUsername())
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
