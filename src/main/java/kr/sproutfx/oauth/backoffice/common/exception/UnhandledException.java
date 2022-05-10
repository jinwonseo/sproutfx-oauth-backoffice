package kr.sproutfx.oauth.backoffice.common.exception;

import kr.sproutfx.oauth.backoffice.common.base.BaseException;

public class UnhandledException extends BaseException {

    public UnhandledException() {
        super("-1", "Unhandled exception", null);
    }

    public UnhandledException(String reason) {
        super("-1", reason, null);
    }
}
