package kr.sproutfx.oauth.backoffice.api.project.controller;

import kr.sproutfx.oauth.backoffice.api.client.entity.Client;
import kr.sproutfx.oauth.backoffice.api.project.entity.Project;
import kr.sproutfx.oauth.backoffice.api.project.enumeration.ProjectStatus;
import kr.sproutfx.oauth.backoffice.api.project.service.ProjectCommandService;
import kr.sproutfx.oauth.backoffice.api.project.service.ProjectQueryService;
import kr.sproutfx.oauth.backoffice.common.exception.InvalidArgumentException;
import kr.sproutfx.oauth.backoffice.common.response.base.BaseResponse;
import kr.sproutfx.oauth.backoffice.common.response.entity.StructuredResponseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = ProjectController.REQUEST_PATH)
public class ProjectController {
    public static final String REQUEST_PATH = "/projects";
    private final ProjectCommandService projectCommandService;
    private final ProjectQueryService projectQueryService;

    public ProjectController(ProjectCommandService projectCommandService, ProjectQueryService projectQueryService) {
        this.projectCommandService = projectCommandService;
        this.projectQueryService = projectQueryService;
    }

    @GetMapping
    public StructuredResponseEntity findAll() {

        return StructuredResponseEntity.succeeded(this.projectQueryService.findAll().stream().map(ProjectResponse::new).collect(toList()));
    }

    @GetMapping(value = "/clients")
    public StructuredResponseEntity findAllWithClients() {

        return StructuredResponseEntity.succeeded(this.projectQueryService.findAllWithClients().stream().map(ProjectWithClientsResponse::new).collect(toList()));
    }

    @GetMapping("/{id}")
    public StructuredResponseEntity findById(@PathVariable UUID id) {

        return StructuredResponseEntity.succeeded(new ProjectResponse(this.projectQueryService.findById(id)));
    }

    @PostMapping
    public StructuredResponseEntity create(@RequestBody @Validated ProjectCreateRequest projectCreateRequest, UriComponentsBuilder uriComponentsBuilder, Errors errors) {

        if (errors.hasErrors()) throw new InvalidArgumentException();

        UUID id = this.projectCommandService.create(projectCreateRequest.getName(), projectCreateRequest.getDescription());

        return StructuredResponseEntity.created(uriComponentsBuilder.path(String.format("%s/%s", REQUEST_PATH, id)).build().toUri(), new ProjectResponse(this.projectQueryService.findById(id)));
    }

    @PutMapping("/{id}")
    public StructuredResponseEntity update(@PathVariable UUID id, @RequestBody @Validated ProjectUpdateRequest projectUpdateRequest, Errors errors) {

        if (errors.hasErrors()) throw new InvalidArgumentException();

        this.projectCommandService.update(id, projectUpdateRequest.getName(), projectUpdateRequest.getDescription());

        return StructuredResponseEntity.succeeded(new ProjectResponse(this.projectQueryService.findById(id)));
    }

    @PatchMapping("/{id}/status")
    public StructuredResponseEntity updateStatus(@PathVariable UUID id, @RequestBody @Validated ProjectStatusUpdateRequest projectStatusUpdateRequest, Errors errors) {

        if (errors.hasErrors()) throw new InvalidArgumentException();

        this.projectCommandService.updateStatus(id, projectStatusUpdateRequest.getProjectStatus());

        return StructuredResponseEntity.succeeded(new ProjectResponse(this.projectQueryService.findById(id)));
    }

    @DeleteMapping("/{id}")
    public StructuredResponseEntity delete(@PathVariable UUID id) {

        this.projectCommandService.delete(id);

        return StructuredResponseEntity.deleted();
    }

    @Getter @Setter
    private static class ProjectWithClientsResponse extends BaseResponse {
        private final String name;
        private final String status;
        private final String description;
        private final List<ClientResponse> clients;

        public ProjectWithClientsResponse(Project project) {
            super(project.getId());
            this.name = project.getName();
            this.status = project.getStatus().toString();
            this.description = project.getDescription();
            this.clients = project.getClients().stream().map(ClientResponse::new).collect(toList());
        }
    }

    @Getter @Setter
    private static class ProjectResponse extends BaseResponse {
        private final String name;
        private final String status;
        private final String description;

        public ProjectResponse(Project project) {
            super(project.getId());
            this.name = project.getName();
            this.status = project.getStatus().toString();
            this.description = project.getDescription();
        }
    }

    @Getter @Setter
    private static class ClientResponse extends BaseResponse {
        private final String code;
        private final String name;
        private final String status;
        private final String description;

        public ClientResponse(Client client) {
            super(client.getId());
            this.code = client.getCode();
            this.name = client.getName();
            this.status = client.getStatus().toString();
            this.description = client.getDescription();
        }
    }

    @Data
    private static class ProjectCreateRequest {
        @NotBlank
        private String name;
        private String description;
    }

    @Data
    private static class ProjectUpdateRequest {
        @NotBlank
        private String name;
        private String description;
    }

    @Data
    private static class ProjectStatusUpdateRequest {
        private ProjectStatus projectStatus;
    }
}
