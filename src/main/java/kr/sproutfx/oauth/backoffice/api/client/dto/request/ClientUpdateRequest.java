package kr.sproutfx.oauth.backoffice.api.client.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ClientUpdateRequest {
    @NotBlank
    private String name;
    private String description;
}
