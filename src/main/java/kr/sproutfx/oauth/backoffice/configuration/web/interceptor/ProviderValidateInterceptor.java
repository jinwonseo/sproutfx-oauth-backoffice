package kr.sproutfx.oauth.backoffice.configuration.web.interceptor;

import kr.sproutfx.oauth.backoffice.common.utilities.CryptoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
public class ProviderValidateInterceptor implements HandlerInterceptor {
    @Autowired
    private ProviderValidateInterceptorProperties properties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return Objects.equals(properties.getValidationValue(), CryptoUtils.decrypt(request.getHeader(properties.getValidationHeader())));
    }
}
