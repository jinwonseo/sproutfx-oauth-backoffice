package kr.sproutfx.oauth.backoffice.configuration.oauth.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
@ConfigurationProperties(prefix = "sproutfx.oauth.resource-server")
public class ResourceServerProperties {
    private UUID id;
    private String validationHeader;
    private String validationKey;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getValidationHeader() {
        return validationHeader;
    }

    public void setValidationHeader(String validationHeader) {
        this.validationHeader = validationHeader;
    }

    public String getValidationKey() {
        return validationKey;
    }

    public void setValidationKey(String validationKey) {
        this.validationKey = validationKey;
    }
}
