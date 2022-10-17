package kr.sproutfx.oauth.backoffice.api.project.controller;

import kr.sproutfx.oauth.backoffice.api.project.dto.response.ProjectResponse;
import kr.sproutfx.oauth.backoffice.api.project.service.ProjectQueryService;
import kr.sproutfx.oauth.backoffice.common.response.entity.StructuredResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = ProjectAuthorizeController.REQUEST_PATH)
public class ProjectAuthorizeController {
    public static final String REQUEST_PATH = "/de7e284c-38ef-46fb-b911-12ad2faf8623/projects";
    private final ProjectQueryService projectQueryService;

    public ProjectAuthorizeController(ProjectQueryService projectQueryService) {
        this.projectQueryService = projectQueryService;
    }

    @GetMapping(value = "/{id}")
    public StructuredResponseEntity findById(@PathVariable UUID id) {
        return StructuredResponseEntity.succeeded(
                new ProjectResponse(this.projectQueryService.findById(id))
        );
    }
}
