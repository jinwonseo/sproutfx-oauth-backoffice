package kr.sproutfx.oauth.backoffice.api.client.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ClientType {
    AUTHORIZATION_SERVER, RESOURCE_SERVER, APPLICATION_SERVER;

    @JsonCreator
    public static ClientType fromValue(String value) {
        return ClientType.valueOf(value.toUpperCase());
    }
}
