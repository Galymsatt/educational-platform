package kz.edu.platform.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;


@Component
@RequiredArgsConstructor
public class GenerateEmailService {

    private final SpringTemplateEngine thymeleafTemplateEngine;

    public String generate(String hashCode) {
        Context context = new Context();
        context.setVariable("url" ,  "domen"+"/api/v1/auth/password/"+hashCode);
        return thymeleafTemplateEngine.process("test", context);
    }
}
