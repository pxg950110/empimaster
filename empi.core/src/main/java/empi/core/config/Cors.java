package empi.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author pxg
 * @Date 2019/8/25
 * @Time 15:51
 * @Version v1.0
 * @mail pxg950110@163.com
 * @description 解决跨域问题
 */
@Configuration
@EnableWebMvc
public class Cors extends WebMvcConfigurerAdapter {
//    @Value("${spring.web.page.url}")
//    private String pageUrlpageUrl;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://127.0.0.1:57000","http://localhost:57000","http://localhost:57000")
                .allowedMethods("GET", "POST","PUT", "OPTIONS", "DELETE", "PATCH")
                .allowCredentials(true).maxAge(3600);
//        registry.addMapping("/**")
//                .allowedOrigins("http://127.0.0.1:54500")
//                .allowedMethods("GET","POST","OPTIONS","DELETE","PATCH")
//                .allowCredentials(true).maxAge(3600);
    }
}
