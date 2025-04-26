package com.example.back.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ReviewWebConfig implements WebMvcConfigurer {
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置靜態資源路徑，例如上傳的圖片
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:./uploads/");
    }
}