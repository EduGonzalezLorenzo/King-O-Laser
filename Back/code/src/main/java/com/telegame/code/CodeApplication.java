package com.telegame.code;

import com.telegame.code.interceptors.TokenInterceptor;
import com.telegame.code.interceptors.UserInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AllArgsConstructor
@SpringBootApplication
public class CodeApplication implements WebMvcConfigurer {
    TokenInterceptor tokenInterceptor;
    UserInterceptor userInterceptor;

    public static void main(String[] args) {
        SpringApplication.run(CodeApplication.class, args);
    }

    public void addInterceptors(InterceptorRegistry registry) {
        //URLs where you need to be logged
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/match/**")
                .addPathPatterns("/getPlayer")
                .addPathPatterns("/update/**")
                .addPathPatterns("/settings")
                .addPathPatterns("/delete/**");

        //URLs where you need to be NO logged
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/login")
                .addPathPatterns("/signUp");
    }
}
