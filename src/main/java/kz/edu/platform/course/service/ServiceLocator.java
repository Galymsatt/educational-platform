package kz.edu.platform.course.service;

import kz.edu.platform.security.service.impl.UserServiceImpl;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public final class ServiceLocator implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        context = ac;
    }

    public static UserServiceImpl userService() {
        return context.getBean(UserServiceImpl.class);
    }
}
