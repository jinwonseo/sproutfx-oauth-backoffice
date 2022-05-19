package kr.sproutfx.oauth.backoffice.common.utilities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientUtils {
    private static String commonGatewayBaseUrl;

    @Value("${sproutfx.web-client.common-gateway.url}")
    public void setCommonGatewayBaseUrl(String value) {
        commonGatewayBaseUrl = value;
    }

    public static WebClient commonGatewayWebClient() {
        return WebClient.builder().baseUrl(commonGatewayBaseUrl).build();
    }
}
