package kr.sproutfx.oauth.backoffice.common.exception;

import kr.sproutfx.oauth.backoffice.common.base.BaseException;
import org.springframework.http.HttpStatus;

public class DecryptFailedException extends BaseException {
    public DecryptFailedException() {
        super("decrypt_failed", "Decrypt failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
