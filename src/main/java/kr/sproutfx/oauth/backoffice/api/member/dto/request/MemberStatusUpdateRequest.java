package kr.sproutfx.oauth.backoffice.api.member.dto.request;

import kr.sproutfx.oauth.backoffice.api.member.enumeration.MemberStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberStatusUpdateRequest {
    @NotBlank
    private MemberStatus memberStatus;
}
