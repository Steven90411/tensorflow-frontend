package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	
//	@Value("${cors.allowed.origins}")//讀取proper裡的cors.allowed.origins設定
//	private String allowedOrigins;  
	
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") //全局配置
                .allowedMethods("*") // 允許的方法
                .allowCredentials(true)
                .allowedHeaders("*");
        
    }
    
}
