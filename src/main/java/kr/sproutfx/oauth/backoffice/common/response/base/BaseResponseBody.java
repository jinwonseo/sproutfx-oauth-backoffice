package kr.sproutfx.oauth.backoffice.common.response.base;

public class BaseResponseBody {
    private Boolean succeeded;

    public BaseResponseBody() { }

    public BaseResponseBody(Boolean succeeded) {
        this.succeeded = succeeded;
    }

    public Boolean isSucceeded() {
        return succeeded;
    }
}
