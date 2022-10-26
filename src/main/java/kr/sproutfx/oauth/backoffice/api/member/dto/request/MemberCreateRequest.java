package kr.sproutfx.oauth.backoffice.api.member.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class MemberCreateRequest {
    @Email
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String password;
    private String description;
}
