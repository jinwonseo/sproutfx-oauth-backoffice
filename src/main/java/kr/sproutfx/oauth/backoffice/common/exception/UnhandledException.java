package kr.sproutfx.oauth.backoffice.common.exception;

import org.springframework.http.HttpStatus;

public class UnhandledException extends BaseException {

    public UnhandledException() {
        super("-1", "Unhandled exception", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public UnhandledException(String reason) {
        super("-1", reason, HttpStatus.BAD_REQUEST);
    }
}
