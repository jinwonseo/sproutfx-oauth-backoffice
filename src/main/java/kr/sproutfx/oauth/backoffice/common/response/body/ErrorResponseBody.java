package kr.sproutfx.oauth.backoffice.common.response.body;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.sproutfx.oauth.backoffice.common.exception.BaseException;
import kr.sproutfx.oauth.backoffice.common.response.base.BaseResponseBody;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseBody extends BaseResponseBody {
    private Error error;

    private ErrorResponseBody() { }

    private ErrorResponseBody(BaseException exception) {
        super(Boolean.FALSE);
        this.error = new Error(exception.getValue(), exception.getReason());
    }

    public Error getError() {
        return error;
    }

    public static ErrorResponseBody error(BaseException exception) {
        return new ErrorResponseBody(exception);
    }

    public static class Error {
        private final String value;
        private final String reason;

        public Error(String value, String reason) {
            this.value = value;
            this.reason = reason;
        }

        public String getValue() {
            return value;
        }

        public String getReason() {
            return reason;
        }
    }
}
