package kr.sproutfx.oauth.backoffice.api.project.dto.request;

import kr.sproutfx.oauth.backoffice.api.project.enumeration.ProjectStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProjectStatusUpdateRequest {
    @NotBlank
    private ProjectStatus projectStatus;
}
