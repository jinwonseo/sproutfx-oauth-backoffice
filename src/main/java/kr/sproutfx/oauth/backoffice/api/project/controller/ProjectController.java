package kr.sproutfx.oauth.backoffice.api.project.controller;

import kr.sproutfx.oauth.backoffice.api.client.entity.Client;
import kr.sproutfx.oauth.backoffice.api.project.entity.Project;
import kr.sproutfx.oauth.backoffice.api.project.enumeration.ProjectStatus;
import kr.sproutfx.oauth.backoffice.api.project.service.ProjectCommandService;
import kr.sproutfx.oauth.backoffice.api.project.service.ProjectQueryService;
import kr.sproutfx.oauth.backoffice.common.base.BaseController;
import kr.sproutfx.oauth.backoffice.common.exception.InvalidArgumentException;
import lombok.Data;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "projects")
public class ProjectController extends BaseController {
    private final ProjectCommandService projectCommandService;
    private final ProjectQueryService projectQueryService;

    public ProjectController(ProjectCommandService projectCommandService, ProjectQueryService projectQueryService) {
        this.projectCommandService = projectCommandService;
        this.projectQueryService = projectQueryService;
    }

    @GetMapping
    public StructuredBody findAll() {

        return StructuredBody.content(this.projectQueryService.findAll().stream().map(ProjectResponse::new).collect(toList()));
    }

    @GetMapping(value = "/clients")
    public StructuredBody findAllWithClients() {

        return StructuredBody.content(this.projectQueryService.findAllWithClients().stream().map(ProjectWithClientsResponse::new).collect(toList()));
    }

    @GetMapping("/{id}")
    public StructuredBody findById(@PathVariable UUID id) {

        return StructuredBody.content(new ProjectResponse(this.projectQueryService.findById(id)));
    }

    @PostMapping
    public StructuredBody create(@RequestBody @Validated ProjectCreateRequest projectCreateRequest, Errors errors) {

        if (errors.hasErrors()) throw new InvalidArgumentException();

        UUID id = this.projectCommandService.create(projectCreateRequest.getName(), projectCreateRequest.getDescription());

        return StructuredBody.content(new ProjectResponse(this.projectQueryService.findById(id)));
    }

    @PutMapping("/{id}")
    public StructuredBody update(@PathVariable UUID id, @RequestBody @Validated ProjectUpdateRequest projectUpdateRequest, Errors errors) {

        if (errors.hasErrors()) throw new InvalidArgumentException();

        this.projectCommandService.update(id, projectUpdateRequest.getName(), projectUpdateRequest.getDescription());

        return StructuredBody.content(new ProjectResponse(this.projectQueryService.findById(id)));
    }

    @PatchMapping("/{id}/status")
    public StructuredBody updateStatus(@PathVariable UUID id, @RequestBody @Validated ProjectStatusUpdateRequest projectStatusUpdateRequest, Errors errors) {

        if (errors.hasErrors()) throw new InvalidArgumentException();

        this.projectCommandService.updateStatus(id, projectStatusUpdateRequest.getProjectStatus());

        return StructuredBody.content(new ProjectResponse(this.projectQueryService.findById(id)));
    }

    @DeleteMapping("/{id}")
    public StructuredBody delete(@PathVariable UUID id) {

        this.projectCommandService.delete(id);

        return StructuredBody.content(new ProjectDeleteResponse(id));
    }

    @Data
    private static class ProjectWithClientsResponse {
        private final UUID id;
        private final String name;
        private final String status;
        private final String description;
        private final List<ClientResponse> clients;

        public ProjectWithClientsResponse(Project project) {
            this.id = project.getId();
            this.name = project.getName();
            this.status = project.getStatus().toString();
            this.description = project.getDescription();
            this.clients = project.getClients().stream().map(ClientResponse::new).collect(toList());
        }
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

    @Data
    private static class ClientResponse {
        private final UUID id;
        private final String code;
        private final String name;
        private final String status;
        private final String description;

        public ClientResponse(Client client) {
            this.id = client.getId();
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

    @Data
    private static class ProjectDeleteResponse {
        private UUID deletedProjectId;

        public ProjectDeleteResponse(UUID id) {
            this.deletedProjectId = id;
        }
    }
}
