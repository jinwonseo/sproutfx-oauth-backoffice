package kr.sproutfx.oauth.backoffice.api.member.exception;

import kr.sproutfx.oauth.backoffice.common.exception.BaseException;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class MemberNotFoundException extends BaseException {

    public MemberNotFoundException() {
        super("member_not_found", "Member not found", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public MemberNotFoundException(UUID id) {
        super("member_not_found", String.format("Member(%s) not found", id.toString()), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
