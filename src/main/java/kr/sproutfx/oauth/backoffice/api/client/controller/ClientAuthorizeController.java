package kr.sproutfx.oauth.backoffice.api.client.controller;

import kr.sproutfx.oauth.backoffice.api.client.entity.Client;
import kr.sproutfx.oauth.backoffice.api.client.service.ClientQueryService;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/de7e284c-38ef-46fb-b911-12ad2faf8623/clients")
public class ClientAuthorizeController {
    private final ClientQueryService clientQueryService;

    public ClientAuthorizeController(ClientQueryService clientQueryService) {
        this.clientQueryService = clientQueryService;
    }

    @GetMapping(params = {"client-code"})
    public ClientResponse findByCode(@RequestParam(value = "client-code") String code) {
        return new ClientResponse(this.clientQueryService.findByCodeWithProject(code));
    }

    @GetMapping(params = {"client-secret"})
    public ClientResponse findBySecret(@RequestParam(value = "client-secret") String secret) {
        return new ClientResponse(this.clientQueryService.findBySecretWithProject(secret));
    }

    @Data
    private static class ClientResponse {
        private final UUID id;
        private final String code;
        private final String name;
        private final String secret;
        private final String accessTokenSecret;
        private final Long accessTokenValidityInSeconds;
        private final String status;
        private final String description;

        private final UUID projectId;

        public ClientResponse(Client client) {
            this.id = client.getId();
            this.code = client.getCode();
            this.name = client.getName();
            this.secret = client.getSecret();
            this.accessTokenSecret = client.getAccessTokenSecret();
            this.accessTokenValidityInSeconds = client.getAccessTokenValidityInSeconds();
            this.status = (client.getStatus() == null) ? null : client.getStatus().toString();
            this.description = client.getDescription();

            this.projectId = (client.getProject() == null) ? null : client.getProject().getId();
        }
    }
}
