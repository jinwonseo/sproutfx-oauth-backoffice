package kr.sproutfx.oauth.backoffice.configuration.web;

import kr.sproutfx.oauth.backoffice.configuration.web.interceptor.ProviderValidateInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    private ProviderValidateInterceptor providerValidateInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(providerValidateInterceptor)
            .addPathPatterns("/de7e284c-38ef-46fb-b911-12ad2faf8623/**");
    }
}
