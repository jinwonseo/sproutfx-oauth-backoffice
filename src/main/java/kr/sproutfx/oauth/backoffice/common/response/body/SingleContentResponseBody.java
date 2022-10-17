package kr.sproutfx.oauth.backoffice.common.response.body;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.sproutfx.oauth.backoffice.common.response.base.BaseResponse;
import kr.sproutfx.oauth.backoffice.common.response.base.BaseResponseBody;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SingleContentResponseBody<T extends BaseResponse> extends BaseResponseBody {
    private T content;

    private SingleContentResponseBody() { }

    private SingleContentResponseBody(T content) {
        super(Boolean.TRUE);
        this.content = content;
    }

    public T getContent() {
        return content;
    }

    public static <T extends BaseResponse> SingleContentResponseBody<T> content(T content) {
        return new SingleContentResponseBody<>(content);
    }
}