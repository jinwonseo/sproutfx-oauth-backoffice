package kr.sproutfx.oauth.backoffice.common.base;

import com.fasterxml.jackson.annotation.JsonInclude;

public class BaseController {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class StructuredBody {
        private boolean succeeded;
        private Object content;
        private Error error;

        public StructuredBody() {
        }

        private StructuredBody(Object content) {
            this.succeeded = true;
            this.content = content;
            this.error = null;
        }

        private StructuredBody(Throwable error) {
            this.succeeded = false;
            this.content = null;
            this.error = new Error(((BaseException) error).getValue(), ((BaseException) error).getReason());
        }

        public static StructuredBody content(Object content) {
            return new StructuredBody(content);
        }

        public static StructuredBody error(Throwable error) {
            return new StructuredBody(error);
        }

        public boolean isSucceeded() {
            return succeeded;
        }

        public Object getContent() {
            return content;
        }

        public Error getError() {
            return error;
        }
    }

    static class Error {
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
