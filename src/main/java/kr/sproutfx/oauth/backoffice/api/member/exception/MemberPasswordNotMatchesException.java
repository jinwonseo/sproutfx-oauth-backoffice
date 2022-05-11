package kr.sproutfx.oauth.backoffice.api.member.exception;

import kr.sproutfx.oauth.backoffice.common.base.BaseException;
import org.springframework.http.HttpStatus;

public class MemberPasswordNotMatchesException extends BaseException {

    public MemberPasswordNotMatchesException() {
        super("password_not_matches", "Member password not matches.", HttpStatus.UNAUTHORIZED);
    }

}
