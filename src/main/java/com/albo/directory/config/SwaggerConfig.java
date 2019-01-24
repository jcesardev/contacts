package com.albo.directory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.any())
                .paths(paths()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Albo Directory Rest API")
                .description("This page lists all the rest apis for Albo Directory App.")
                .version("1.0")
                .contact(new Contact("Julio Cesar Bolaños Palacios", "https://github.com/jcesardev", "cesar.dev.code@gmail.com")).build();
    }

    private Predicate<String> paths() {
        return Predicates.and(PathSelectors.regex("/usr.*"), Predicates.not(PathSelectors.regex("/error.*")));

    }

}
