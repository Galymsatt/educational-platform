package kz.edu.platform.security.security.contextResolver;

import kz.edu.platform.course.service.ServiceLocator;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@Configuration
public class OAuth2WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserContextArgumentResolver(ServiceLocator.userService()));
    }
}
