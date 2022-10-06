package kr.sproutfx.oauth.backoffice.common.advisor;

import kr.sproutfx.oauth.backoffice.common.base.BaseException;
import kr.sproutfx.oauth.backoffice.common.dto.StructuredBody;
import kr.sproutfx.oauth.backoffice.common.exception.UnhandledException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerAdvisor {
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<StructuredBody> exception(final Throwable t) {
        if (t instanceof RuntimeException && t.getCause() != null && t.getCause() instanceof BaseException) {
            return ResponseEntity.status(((BaseException) t.getCause()).getHttpStatus()).body(StructuredBody.error(t.getCause()));
        } else {
            return ResponseEntity.internalServerError().body(StructuredBody.error(new UnhandledException(t.getLocalizedMessage())));
        }
    }
}