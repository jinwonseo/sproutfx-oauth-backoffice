package kr.sproutfx.oauth.backoffice.common.dto.response.structure;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.sproutfx.oauth.backoffice.common.dto.response.BaseResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SingleContentResponseBodyStructure<T extends BaseResponse> extends BaseResponseBodyStructure {
    private final Boolean succeeded = Boolean.TRUE;
    private final T content;

    private SingleContentResponseBodyStructure(T content) {
        this.content = content;
    }

    public Boolean isSucceeded() {
        return succeeded;
    }

    public T getContent() {
        return content;
    }

    public static <T extends BaseResponse> SingleContentResponseBodyStructure<T> content(T content) {
        return new SingleContentResponseBodyStructure<>(content);
    }
}