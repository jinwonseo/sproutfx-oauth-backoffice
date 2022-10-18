package kr.sproutfx.oauth.backoffice.api.resource.exception;

import kr.sproutfx.oauth.backoffice.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidResourceServerId extends BaseException {
    public InvalidResourceServerId() {
        super("invalid_resource_server_id", "Invalid resource server.", HttpStatus.BAD_REQUEST);
    }
}
