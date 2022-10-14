package kr.sproutfx.oauth.backoffice.common.dto.response.structure;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.sproutfx.oauth.backoffice.common.dto.response.BaseResponse;

import java.util.Collection;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MultiContentResponseBodyStructure<T extends Collection<? extends BaseResponse>> extends BaseResponseBodyStructure {
    private final Boolean succeeded = Boolean.TRUE;
    private final T content;
    private final Integer size;

    private MultiContentResponseBodyStructure(T content) {
        this.content = content;
        this.size = content.size();
    }

    public Boolean isSucceeded() {
        return succeeded;
    }

    public T getContent() {
        return content;
    }

    public Integer getSize() {
        return size;
    }

    public static <T extends Collection<? extends BaseResponse>> MultiContentResponseBodyStructure<T> content(T content) {
        return new MultiContentResponseBodyStructure<>(content);
    }
}
