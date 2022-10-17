package kr.sproutfx.oauth.backoffice.common.response.body;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.sproutfx.oauth.backoffice.common.response.base.BaseResponse;
import kr.sproutfx.oauth.backoffice.common.response.base.BaseResponseBody;

import java.util.Collection;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MultiContentResponseBody<T extends Collection<? extends BaseResponse>> extends BaseResponseBody {
    private T content;
    private Integer size;

    private MultiContentResponseBody() { }

    private MultiContentResponseBody(T content) {
        super(Boolean.TRUE);
        this.content = content;
        this.size = content.size();
    }

    public T getContent() {
        return content;
    }

    public Integer getSize() {
        return size;
    }

    public static <T extends Collection<? extends BaseResponse>> MultiContentResponseBody<T> content(T content) {
        return new MultiContentResponseBody<>(content);
    }
}
