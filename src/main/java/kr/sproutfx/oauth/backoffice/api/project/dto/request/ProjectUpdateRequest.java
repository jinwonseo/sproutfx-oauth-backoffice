package kr.sproutfx.oauth.backoffice.api.project.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProjectUpdateRequest {
    @NotBlank
    private String name;
    private String description;
}
