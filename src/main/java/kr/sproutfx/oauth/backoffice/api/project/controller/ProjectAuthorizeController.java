package kr.sproutfx.oauth.backoffice.api.project.controller;

import kr.sproutfx.oauth.backoffice.api.project.entity.Project;
import kr.sproutfx.oauth.backoffice.api.project.service.ProjectQueryService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/de7e284c-38ef-46fb-b911-12ad2faf8623/projects")
public class ProjectAuthorizeController {
    private final ProjectQueryService projectQueryService;

    public ProjectAuthorizeController(ProjectQueryService projectQueryService) {
        this.projectQueryService = projectQueryService;
    }

    @GetMapping(value = "/{id}")
    public ProjectResponse findById(@RequestHeader("provider-code") String providerCode, @PathVariable UUID id) {
        return new ProjectResponse(this.projectQueryService.findById(id));
    }

    @Data
    private static class ProjectResponse {
        private final UUID id;
        private final String name;
        private final String status;
        private final String description;

        public ProjectResponse(Project project) {
            this.id = project.getId();
            this.name = project.getName();
            this.status = project.getStatus().toString();
            this.description = project.getDescription();
        }
    }
}
