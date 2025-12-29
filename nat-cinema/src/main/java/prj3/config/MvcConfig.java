package prj3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String rootPath = System.getProperty("user.dir");

        String uploadPath = "file:///" + rootPath.replace("\\", "/") + "/uploads/";

        // Debug: In ra console để bạn kiểm tra
        System.out.println("=============================================");
        System.out.println("Đường dẫn ảnh đã cấu hình: " + uploadPath);
        System.out.println("=============================================");

        // 3. Cấu hình
        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadPath);
    }
}