package com.volare_automation.springwebshop.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.nio.charset.StandardCharsets;

@Configuration
public class ThymeleafEngineConfiguration {

    @Bean
    public SpringTemplateEngine springTemplateEngine(){

        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(htmlTemplateResolver());
        return templateEngine;
    }

    @Bean
    public ClassLoaderTemplateResolver htmlTemplateResolver(){

        ClassLoaderTemplateResolver classResolver = new ClassLoaderTemplateResolver();
        classResolver.setPrefix("/templates/");
        classResolver.setSuffix(".html");
        classResolver.setTemplateMode(TemplateMode.HTML);
        classResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        return classResolver;
    }


}
