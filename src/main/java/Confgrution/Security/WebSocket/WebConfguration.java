package Confgrution.Security.WebSocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // فايدته اول مايشتغل يدور عليه ويسويه اكواد الي جوابها كاعدادات للمشروع تقريبا و كومبونت كفلتر وسيرفرس كخدمه
public class WebConfguration {
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return  new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://127.0.0.1:5500","http://localhost:5500","http://localhost:3000")
                        .allowedMethods("GET","POST","PUT","DELETE","OPTIONS");
            }
        };


    }
}