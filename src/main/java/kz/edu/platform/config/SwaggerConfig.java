package kz.edu.platform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.singletonList;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUME = new HashSet<>(Arrays.asList("application/json"));

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(
                        singletonList(
                                new ParameterBuilder()
                                        .name("Authorization")
                                        .modelRef(new ModelRef("string"))
                                        .parameterType("header")
                                        .required(true)
                                        .hidden(true)
                                        .defaultValue("Bearer access_token")
                                        .build()
                        )
                )
                .produces(DEFAULT_PRODUCES_AND_CONSUME)
                .consumes(DEFAULT_PRODUCES_AND_CONSUME)
                .apiInfo(apiInfoAll()).select()
                .apis(RequestHandlerSelectors.basePackage("kz.edu.platform")).paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfoAll() {
        return new ApiInfoBuilder()
                .title("Course REST API")
                .version("1.0.0")
                .build();
    }

}
