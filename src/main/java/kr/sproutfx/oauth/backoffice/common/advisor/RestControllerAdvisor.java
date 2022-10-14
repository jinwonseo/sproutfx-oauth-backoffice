package kr.sproutfx.oauth.backoffice.common.advisor;

import kr.sproutfx.oauth.backoffice.common.dto.response.StructuredResponseEntity;
import kr.sproutfx.oauth.backoffice.common.exception.BaseException;
import kr.sproutfx.oauth.backoffice.common.exception.UnhandledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerAdvisor {
    @ExceptionHandler(Throwable.class)
    public StructuredResponseEntity exception(final Throwable t) {
        if (t instanceof BaseException) {
            return StructuredResponseEntity.error((BaseException) t);
        } else if (t.getCause() != null && t.getCause() instanceof BaseException) {
            return StructuredResponseEntity.error((BaseException) t.getCause());
        } else {
            return StructuredResponseEntity.error(new UnhandledException(String.format("%s: %s", t.getClass().toString(), t.getLocalizedMessage())));
        }
    }
}