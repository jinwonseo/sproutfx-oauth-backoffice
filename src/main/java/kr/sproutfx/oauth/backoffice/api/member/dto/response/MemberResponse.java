package kr.sproutfx.oauth.backoffice.api.member.dto.response;

import kr.sproutfx.oauth.backoffice.api.member.entity.Member;
import kr.sproutfx.oauth.backoffice.common.response.base.BaseResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponse extends BaseResponse {
    private String email;
    private String name;
    private String password;
    private LocalDateTime passwordExpired;
    private String status;
    private String description;

    public MemberResponse(Member member) {
        super(member.getId());
        this.email = member.getEmail();
        this.name = member.getName();
        this.password = member.getPassword();
        this.passwordExpired = member.getPasswordExpired();
        this.status = member.getStatus().toString();
        this.description = member.getDescription();
    }
}
