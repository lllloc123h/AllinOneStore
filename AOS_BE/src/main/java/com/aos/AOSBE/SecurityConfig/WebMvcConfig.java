package com.aos.AOSBE.SecurityConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Tạo đường dẫn tuyệt đối đến thư mục upload
        Path uploadDir = Paths.get("uploads/reviews");
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        // Khi client truy cập /uploads/reviews/** → ánh xạ tới thư mục trong máy chủ
        registry.addResourceHandler("/uploads/reviews/**")
                .addResourceLocations("file:" + uploadPath + "/");
    }
}
