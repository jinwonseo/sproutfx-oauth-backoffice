package kr.sproutfx.oauth.backoffice.configuration.web.interceptor;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "sproutfx.web.interceptor.provider-validate-interceptor")
public class ProviderValidateInterceptorProperties {
    private String validationHeader;
    private String validationSecret;
    private List<String> patterns;

    public String getValidationHeader() {
        return validationHeader;
    }

    public void setValidationHeader(String validationHeader) {
        this.validationHeader = validationHeader;
    }

    public String getValidationSecret() {
        return validationSecret;
    }

    public void setValidationSecret(String validationSecret) {
        this.validationSecret = validationSecret;
    }

    public List<String> getPatterns() {
        return patterns;
    }

    public void setPatterns(List<String> patterns) {
        this.patterns = patterns;
    }
}
