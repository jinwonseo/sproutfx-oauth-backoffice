package kr.sproutfx.oauth.backoffice.common.response.entity;

import kr.sproutfx.oauth.backoffice.common.exception.BaseException;
import kr.sproutfx.oauth.backoffice.common.response.base.BaseResponse;
import kr.sproutfx.oauth.backoffice.common.response.base.BaseResponseBody;
import kr.sproutfx.oauth.backoffice.common.response.body.ErrorResponseBody;
import kr.sproutfx.oauth.backoffice.common.response.body.MultiContentResponseBody;
import kr.sproutfx.oauth.backoffice.common.response.body.SingleContentResponseBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.util.Collection;

public class StructuredResponseEntity extends ResponseEntity<BaseResponseBody> {

    private StructuredResponseEntity(BaseResponseBody body, HttpStatus status) {
        super(body, status);
    }

    private StructuredResponseEntity(BaseResponseBody body, MultiValueMap<String, String> headers, HttpStatus status) {
        super(body, headers, status);
    }

    public StructuredResponseEntity(HttpStatus status) {
        super(status);
    }

    public static <C extends Collection<? extends BaseResponse>> StructuredResponseEntity succeeded(C collection) {
        return new StructuredResponseEntity(MultiContentResponseBody.content(collection), HttpStatus.OK);
    }

    public static <R extends BaseResponse> StructuredResponseEntity succeeded(R response) {
        return new StructuredResponseEntity(SingleContentResponseBody.content(response), HttpStatus.OK);
    }

    public static <R extends BaseResponse> StructuredResponseEntity created(URI uri, R response) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);

        return new StructuredResponseEntity(SingleContentResponseBody.content(response), httpHeaders, HttpStatus.CREATED);
    }

    public static StructuredResponseEntity deleted() {
        return new StructuredResponseEntity(HttpStatus.NO_CONTENT);
    }

    public static <E extends BaseException> StructuredResponseEntity error(E exception) {
        return new StructuredResponseEntity(ErrorResponseBody.error(exception), exception.getHttpStatus());
    }
}
