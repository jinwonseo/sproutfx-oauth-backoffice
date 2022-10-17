package kr.sproutfx.oauth.backoffice.api.project.dto.response;

import kr.sproutfx.oauth.backoffice.api.project.entity.Project;
import kr.sproutfx.oauth.backoffice.common.response.base.BaseResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectResponse extends BaseResponse {
    private String name;
    private String status;
    private String description;

    public ProjectResponse(Project project) {
        super(project.getId());
        this.name = project.getName();
        this.status = project.getStatus().toString();
        this.description = project.getDescription();
    }
}
