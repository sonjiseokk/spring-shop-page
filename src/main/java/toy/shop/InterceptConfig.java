package toy.shop;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import toy.shop.interceptor.AdminInterceptor;
import toy.shop.interceptor.LoginInterceptor;

@Configuration
public class InterceptConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns("/admin/**");

        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/member/login.do");
    }
}
