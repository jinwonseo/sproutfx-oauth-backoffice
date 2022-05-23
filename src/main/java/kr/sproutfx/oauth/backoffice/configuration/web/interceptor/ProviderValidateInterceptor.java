package kr.sproutfx.oauth.backoffice.configuration.web.interceptor;

import kr.sproutfx.oauth.backoffice.common.utilities.CryptoUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
public class ProviderValidateInterceptor implements HandlerInterceptor {
    @Value("${sproutfx.web-client.validation-header.header}")
    private String webClientRequestValidationHeader;

    @Value("${sproutfx.web-client.validation-header.value}")
    private String webClientRequestValidationHeaderValue;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return Objects.equals(webClientRequestValidationHeaderValue, CryptoUtils.decrypt(request.getHeader(webClientRequestValidationHeader)));
    }
}
