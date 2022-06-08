package kr.sproutfx.oauth.backoffice.configuration.web.interceptor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "sproutfx.web.interceptor.provider-validate-interceptor")
public class ProviderValidateInterceptorProperties {
    private String validationHeader;
    private String validationValue;
    private List<String> patterns;

    public String getValidationHeader() {
        return validationHeader;
    }

    public void setValidationHeader(String validationHeader) {
        this.validationHeader = validationHeader;
    }

    public String getValidationValue() {
        return validationValue;
    }

    public void setValidationValue(String validationValue) {
        this.validationValue = validationValue;
    }

    public List<String> getPatterns() {
        return patterns;
    }

    public void setPatterns(List<String> patterns) {
        this.patterns = patterns;
    }
}
