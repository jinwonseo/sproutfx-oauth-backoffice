package kr.sproutfx.oauth.backoffice.api.client.controller;

import kr.sproutfx.oauth.backoffice.api.client.dto.response.ClientResponse;
import kr.sproutfx.oauth.backoffice.api.client.service.ClientQueryService;
import kr.sproutfx.oauth.backoffice.api.resource.exception.InvalidResourceServerId;
import kr.sproutfx.oauth.backoffice.common.response.entity.StructuredResponseEntity;
import kr.sproutfx.oauth.backoffice.configuration.oauth.properties.ResourceServerProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = ClientAuthorizeController.REQUEST_PATH)
public class ClientAuthorizeController {
    public static final String REQUEST_PATH = "/resource-servers/{resource-server-id}/clients";

    private final ResourceServerProperties resourceServerProperties;
    private final ClientQueryService clientQueryService;

    public ClientAuthorizeController(ResourceServerProperties resourceServerProperties, ClientQueryService clientQueryService) {
        this.resourceServerProperties = resourceServerProperties;
        this.clientQueryService = clientQueryService;
    }

    @GetMapping(params = {"client-code"})
    public StructuredResponseEntity findByCode(@PathVariable(value = "resource-server-id") UUID resourceServerId, @RequestParam(value = "client-code") String code) {

        if (!resourceServerProperties.getId().equals(resourceServerId)) throw new InvalidResourceServerId();

        return StructuredResponseEntity.succeeded(
                new ClientResponse(this.clientQueryService.findByCodeWithProject(code))
        );
    }

    @GetMapping(params = {"client-secret"})
    public StructuredResponseEntity findBySecret(@PathVariable(value = "resource-server-id") UUID resourceServerId, @RequestParam(value = "client-secret") String secret) {

        if (!resourceServerProperties.getId().equals(resourceServerId)) throw new InvalidResourceServerId();

        return StructuredResponseEntity.succeeded(
                new ClientResponse(this.clientQueryService.findBySecretWithProject(secret))
        );
    }
}
