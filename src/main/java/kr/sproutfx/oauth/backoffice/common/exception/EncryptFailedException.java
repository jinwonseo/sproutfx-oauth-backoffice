package kr.sproutfx.oauth.backoffice.common.exception;

import org.springframework.http.HttpStatus;

public class EncryptFailedException extends BaseException {

    public EncryptFailedException() {
        super("encrypt_failed", "Encrypt failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
