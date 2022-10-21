package kr.sproutfx.oauth.backoffice.api.resource.controller;

import kr.sproutfx.oauth.backoffice.api.client.dto.response.ClientResponse;
import kr.sproutfx.oauth.backoffice.api.client.service.ClientQueryService;
import kr.sproutfx.oauth.backoffice.api.member.dto.response.MemberResponse;
import kr.sproutfx.oauth.backoffice.api.member.service.MemberQueryService;
import kr.sproutfx.oauth.backoffice.api.project.dto.response.ProjectResponse;
import kr.sproutfx.oauth.backoffice.api.project.service.ProjectQueryService;
import kr.sproutfx.oauth.backoffice.api.resource.exception.InvalidResourceServerId;
import kr.sproutfx.oauth.backoffice.common.response.entity.StructuredResponseEntity;
import kr.sproutfx.oauth.backoffice.configuration.oauth.properties.ResourceServerProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = ResourceController.REQUEST_PATH)
public class ResourceController {
    public static final String REQUEST_PATH = "/resources/{resource-server-id}";

    private final ResourceServerProperties resourceServerProperties;
    private final ProjectQueryService projectQueryService;
    private final ClientQueryService clientQueryService;
    private final MemberQueryService memberQueryService;

    public ResourceController(ResourceServerProperties resourceServerProperties, ProjectQueryService projectQueryService, ClientQueryService clientQueryService, MemberQueryService memberQueryService) {
        this.resourceServerProperties = resourceServerProperties;
        this.projectQueryService = projectQueryService;
        this.clientQueryService = clientQueryService;
        this.memberQueryService = memberQueryService;
    }

    @GetMapping(value = "/projects/{id}")
    public StructuredResponseEntity projectFindById(@PathVariable(value = "resource-server-id") UUID resourceServerId, @PathVariable UUID id) {

        if (!resourceServerProperties.getId().equals(resourceServerId)) throw new InvalidResourceServerId();

        return StructuredResponseEntity.succeeded(
                new ProjectResponse(this.projectQueryService.findById(id))
        );
    }

    @GetMapping(value = "/clients")
    public StructuredResponseEntity clientFindAll(@PathVariable(value = "resource-server-id") UUID resourceServerId) {
        if (!resourceServerProperties.getId().equals(resourceServerId)) throw new InvalidResourceServerId();

        return StructuredResponseEntity.succeeded(
                this.clientQueryService.findAll().stream().map(ClientResponse::new).collect(Collectors.toList())
        );
    }

    @GetMapping(value = "/clients", params = {"client-code"})
    public StructuredResponseEntity clientFindByCode(@PathVariable(value = "resource-server-id") UUID resourceServerId, @RequestParam(value = "client-code") String code) {

        if (!resourceServerProperties.getId().equals(resourceServerId)) throw new InvalidResourceServerId();

        return StructuredResponseEntity.succeeded(
                new ClientResponse(this.clientQueryService.findByCodeWithProject(code))
        );
    }

    @GetMapping(value = "/clients", params = {"client-secret"})
    public StructuredResponseEntity clientFindBySecret(@PathVariable(value = "resource-server-id") UUID resourceServerId, @RequestParam(value = "client-secret") String secret) {

        if (!resourceServerProperties.getId().equals(resourceServerId)) throw new InvalidResourceServerId();

        return StructuredResponseEntity.succeeded(
                new ClientResponse(this.clientQueryService.findBySecretWithProject(secret))
        );
    }

    @GetMapping(value = "/members/{id}")
    public StructuredResponseEntity findById(@PathVariable(value = "resource-server-id") UUID resourceServerId, @PathVariable UUID id) {

        if (!resourceServerProperties.getId().equals(resourceServerId)) throw new InvalidResourceServerId();

        return StructuredResponseEntity.succeeded(
                new MemberResponse(this.memberQueryService.findById(id))
        );
    }

    @GetMapping(value = "/members", params = {"email"})
    public StructuredResponseEntity findByEmail(@PathVariable(value = "resource-server-id") UUID resourceServerId, @RequestParam String email) {

        if (!resourceServerProperties.getId().equals(resourceServerId)) throw new InvalidResourceServerId();

        return StructuredResponseEntity.succeeded(
                new MemberResponse(this.memberQueryService.findByEmail(email))
        );
    }
}
