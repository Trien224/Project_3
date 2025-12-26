package com.dttlibrary.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Lấy đường dẫn tuyệt đối đến thư mục gốc của dự án
        String projectPath = Paths.get("").toAbsolutePath().toString();
        
        // Đường dẫn đến thư mục uploads
        String uploadDir = "src/main/resources/static/uploads/";
        Path uploadPath = Paths.get(projectPath, uploadDir);

        // Đăng ký resource handler
        // Ánh xạ URL pattern /uploads/** tới thư mục vật lý
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:/" + uploadPath.toString().replace("\\", "/") + "/");
    }
}
