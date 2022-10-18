package kr.sproutfx.oauth.backoffice.configuration.web.interceptor;

import kr.sproutfx.oauth.backoffice.common.utilities.CryptoUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
@EnableConfigurationProperties({ ProviderValidateInterceptorProperties.class })
public class ProviderValidateInterceptor implements HandlerInterceptor {
    private final ProviderValidateInterceptorProperties properties;

    public ProviderValidateInterceptor(ProviderValidateInterceptorProperties properties) {
        this.properties = properties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        return Objects.equals(properties.getValidationSecret(), CryptoUtils.decrypt(request.getHeader(properties.getValidationHeader())));
    }
}
