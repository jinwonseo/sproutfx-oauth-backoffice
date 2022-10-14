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

    public static <C extends Collection<? extends BaseResponse>> StructuredResponseEntity succeeded(C collection) {
        return new StructuredResponseEntity(MultiContentResponseBodyStructure.content(collection), HttpStatus.OK);
    }

    public static <R extends BaseResponse> StructuredResponseEntity succeeded(R response) {
        return new StructuredResponseEntity(SingleContentResponseBodyStructure.content(response), HttpStatus.OK);
    }

    public static <R extends BaseResponse> StructuredResponseEntity created(URI uri, R response) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);

        return new StructuredResponseEntity(SingleContentResponseBodyStructure.content(response), httpHeaders, HttpStatus.CREATED);
    }

    public static StructuredResponseEntity deleted() {
        return new StructuredResponseEntity(HttpStatus.NO_CONTENT);
    }

    public static <E extends BaseException> StructuredResponseEntity error(E exception) {
        return new StructuredResponseEntity(ErrorResponseBodyStructure.error(exception), exception.getHttpStatus());
    }
}
