package kr.sproutfx.oauth.backoffice.configuration.web.interceptor;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties({ ProviderValidateInterceptorProperties.class })
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final ProviderValidateInterceptor interceptor;
    private final ProviderValidateInterceptorProperties properties;

    public WebMvcConfiguration(ProviderValidateInterceptor interceptor, ProviderValidateInterceptorProperties properties) {
        this.interceptor = interceptor;
        this.properties = properties;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns(properties.getPatterns());
    }
}
