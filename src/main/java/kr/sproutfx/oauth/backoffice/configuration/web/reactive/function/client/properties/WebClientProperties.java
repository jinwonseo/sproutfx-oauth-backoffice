package kr.sproutfx.oauth.backoffice.configuration.web.reactive.function.client.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sproutfx.web-client")
public class WebClientProperties {
    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public void setGatewayUrl(String gatewayUrl) {
        this.gatewayUrl = gatewayUrl;
    }

    private String gatewayUrl;
}
