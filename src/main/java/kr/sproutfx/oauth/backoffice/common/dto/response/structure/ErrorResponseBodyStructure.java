package kr.sproutfx.oauth.backoffice.common.dto.response.structure;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.sproutfx.oauth.backoffice.common.exception.BaseException;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseBodyStructure extends BaseResponseBodyStructure {
    private final Boolean succeeded = Boolean.FALSE;
    private final Error error;

    private ErrorResponseBodyStructure(BaseException exception) {
        this.error = new Error(exception.getValue(), exception.getReason());
    }

    public Boolean isSucceeded() {
        return succeeded;
    }

    public Error getError() {
        return error;
    }

    public static ErrorResponseBodyStructure error(BaseException exception) {
        return new ErrorResponseBodyStructure(exception);
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
