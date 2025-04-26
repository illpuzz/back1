package com.example.back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

@Configuration
public class ReviewSwaggerConfig {
    
    @Bean
    public OpenAPI campReviewOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("營地評價 API")
                        .description("營地評價系統的REST API文檔")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("營地評價系統開發團隊")
                                .email("support@campreview.com")));
    }
}