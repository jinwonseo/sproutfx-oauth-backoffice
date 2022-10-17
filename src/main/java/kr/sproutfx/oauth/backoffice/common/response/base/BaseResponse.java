package kr.sproutfx.oauth.backoffice.common.response.base;

import java.util.UUID;

public class BaseResponse {
    private UUID id;

    public BaseResponse() { }

    public BaseResponse(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
