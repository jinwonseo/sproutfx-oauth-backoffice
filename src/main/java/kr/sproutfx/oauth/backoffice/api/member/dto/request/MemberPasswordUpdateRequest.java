package kr.sproutfx.oauth.backoffice.api.member.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberPasswordUpdateRequest {
    @NotBlank
    private String currentPassword;
    @NotBlank
    private String newPassword;
}
