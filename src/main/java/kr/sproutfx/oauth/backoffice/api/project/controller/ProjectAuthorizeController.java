package kr.sproutfx.oauth.backoffice.api.project.controller;

import kr.sproutfx.oauth.backoffice.api.project.dto.response.ProjectResponse;
import kr.sproutfx.oauth.backoffice.api.project.service.ProjectQueryService;
import kr.sproutfx.oauth.backoffice.api.resource.exception.InvalidResourceServerId;
import kr.sproutfx.oauth.backoffice.common.response.entity.StructuredResponseEntity;
import kr.sproutfx.oauth.backoffice.configuration.oauth.properties.ResourceServerProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = ProjectAuthorizeController.REQUEST_PATH)
public class ProjectAuthorizeController {
    public static final String REQUEST_PATH = "/resource-servers/{resource-server-id}/projects";

    private final ResourceServerProperties resourceServerProperties;
    private final ProjectQueryService projectQueryService;

    public ProjectAuthorizeController(ResourceServerProperties resourceServerProperties, ProjectQueryService projectQueryService) {
        this.resourceServerProperties = resourceServerProperties;
        this.projectQueryService = projectQueryService;
    }

    @GetMapping(value = "/{id}")
    public StructuredResponseEntity findById(@PathVariable(value = "resource-server-id") UUID resourceServerId, @PathVariable UUID id) {

        if (!resourceServerProperties.getId().equals(resourceServerId)) throw new InvalidResourceServerId();

        return StructuredResponseEntity.succeeded(
                new ProjectResponse(this.projectQueryService.findById(id))
        );
    }
}
