package com.aos.AOSBE.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("/avatar/**") // URL người dùng truy cập
            .addResourceLocations("file:D:/AllinOneStore/uploads/avatar/"); // Thư mục vật lý
    }
}
