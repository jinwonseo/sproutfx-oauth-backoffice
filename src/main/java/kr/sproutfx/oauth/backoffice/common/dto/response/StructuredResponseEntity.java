package kr.sproutfx.oauth.backoffice.common.dto.response;

import kr.sproutfx.oauth.backoffice.common.dto.response.structure.BaseResponseBodyStructure;
import kr.sproutfx.oauth.backoffice.common.dto.response.structure.ErrorResponseBodyStructure;
import kr.sproutfx.oauth.backoffice.common.dto.response.structure.MultiContentResponseBodyStructure;
import kr.sproutfx.oauth.backoffice.common.dto.response.structure.SingleContentResponseBodyStructure;
import kr.sproutfx.oauth.backoffice.common.exception.BaseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.util.Collection;

public class StructuredResponseEntity extends ResponseEntity<BaseResponseBodyStructure> {

    private StructuredResponseEntity(BaseResponseBodyStructure body, HttpStatus status) {
        super(body, status);
    }

    private StructuredResponseEntity(BaseResponseBodyStructure body, MultiValueMap<String, String> headers, HttpStatus status) {
        super(body, headers, status);
    }

    public StructuredResponseEntity(HttpStatus status) {
        super(status);
    }

    public static <T extends Collection<? extends BaseResponse>> StructuredResponseEntity succeeded(T content) {
        return new StructuredResponseEntity(MultiContentResponseBodyStructure.content(content), HttpStatus.OK);
    }

    public static <T extends BaseResponse> StructuredResponseEntity succeeded(T content) {
        return new StructuredResponseEntity(SingleContentResponseBodyStructure.content(content), HttpStatus.OK);
    }

    public static <T extends BaseResponse> StructuredResponseEntity created(URI uri, T content) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);

        return new StructuredResponseEntity(SingleContentResponseBodyStructure.content(content), httpHeaders, HttpStatus.CREATED);
    }

    public static StructuredResponseEntity deleted() {
        return new StructuredResponseEntity(HttpStatus.NO_CONTENT);
    }

    public static <T extends BaseException> StructuredResponseEntity error(T exception) {
        return new StructuredResponseEntity(ErrorResponseBodyStructure.error(exception), exception.getHttpStatus());
    }
}
