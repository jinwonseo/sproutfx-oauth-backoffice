package kr.sproutfx.oauth.backoffice.api.client.controller;

import kr.sproutfx.oauth.backoffice.api.client.dto.response.ClientResponse;
import kr.sproutfx.oauth.backoffice.api.client.service.ClientQueryService;
import kr.sproutfx.oauth.backoffice.common.response.entity.StructuredResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = ClientAuthorizeController.REQUEST_PATH)
public class ClientAuthorizeController {
    public static final String REQUEST_PATH = "/de7e284c-38ef-46fb-b911-12ad2faf8623/clients";
    private final ClientQueryService clientQueryService;

    public ClientAuthorizeController(ClientQueryService clientQueryService) {
        this.clientQueryService = clientQueryService;
    }

    @GetMapping(params = {"client-code"})
    public StructuredResponseEntity findByCode(@RequestParam(value = "client-code") String code) {
        return StructuredResponseEntity.succeeded(
                new ClientResponse(this.clientQueryService.findByCodeWithProject(code))
        );
    }

    @GetMapping(params = {"client-secret"})
    public StructuredResponseEntity findBySecret(@RequestParam(value = "client-secret") String secret) {
        return StructuredResponseEntity.succeeded(
                new ClientResponse(this.clientQueryService.findBySecretWithProject(secret))
        );
    }
}
