package kr.sproutfx.oauth.backoffice.api.client.controller;

import kr.sproutfx.oauth.backoffice.api.client.entity.Client;
import kr.sproutfx.oauth.backoffice.api.client.enumeration.ClientStatus;
import kr.sproutfx.oauth.backoffice.api.client.service.ClientCommandService;
import kr.sproutfx.oauth.backoffice.api.client.service.ClientQueryService;
import kr.sproutfx.oauth.backoffice.common.base.BaseController;
import kr.sproutfx.oauth.backoffice.common.exception.InvalidArgumentException;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/clients")
public class ClientController extends BaseController {

    private final ClientCommandService clientCommandService;
    private final ClientQueryService clientQueryService;

    public ClientController(ClientCommandService clientCommandService, ClientQueryService clientQueryService) {
        this.clientCommandService = clientCommandService;
        this.clientQueryService = clientQueryService;
    }

    @GetMapping
    public StructuredBody<List<ClientResponse>> findAll() {

        return StructuredBody.content(
            this.clientQueryService.findAll().stream().map(ClientResponse::new).collect(toList()));
    }

    @GetMapping(value = "/{id}")
    public StructuredBody<ClientResponse> findById(@PathVariable("id") UUID id) {

        return StructuredBody.content(
            new ClientResponse(this.clientQueryService.findById(id)));
    }

    @PostMapping
    public StructuredBody<ClientResponse> create(@RequestBody @Validated ClientCreateRequest clientCreateRequest, Errors errors) {

        if (errors.hasErrors()) throw new InvalidArgumentException();

        UUID id = this.clientCommandService.create(clientCreateRequest.getName(), clientCreateRequest.getDescription());

        return StructuredBody.content(
            new ClientResponse(this.clientQueryService.findById(id)));
    }

    @PutMapping(value = "/{id}")
    public StructuredBody<ClientResponse> update(@PathVariable UUID id, @RequestBody @Validated ClientUpdateRequest clientUpdateRequest, Errors errors) {

        if (errors.hasErrors()) throw new InvalidArgumentException();

        String name = clientUpdateRequest.getName();
        Long accessTokenValidityInSeconds = clientUpdateRequest.getAccessTokenValidityInSeconds();

        String description = clientUpdateRequest.getDescription();

        this.clientCommandService.update(id, name, accessTokenValidityInSeconds, description);

        return StructuredBody.content(
            new ClientResponse(this.clientQueryService.findById(id)));
    }

    @PatchMapping("/{id}/status")
    public StructuredBody<ClientResponse> updateStatus(@PathVariable UUID id, @RequestBody ClientStatusUpdateRequest clientStatusUpdateRequest) {

        this.clientCommandService.updateStatus(id, clientStatusUpdateRequest.getClientStatus());

        return StructuredBody.content(
            new ClientResponse(this.clientQueryService.findById(id)));
    }

    @DeleteMapping(value = "/{id}")
    public StructuredBody<ClientDeleteResponse> delete(@PathVariable UUID id) {

        this.clientCommandService.deleteById(id);

        return StructuredBody.content(
            new ClientDeleteResponse(id));
    }

    @Getter
    static class ClientResponse {
        private final UUID id;
        private final String code;
        private final String name;
        private final String status;
        private final String description;

        public ClientResponse(Client client) {
            this.id = client.getId();
            this.code = client.getCode();
            this.name = client.getName();
            this.status = (client.getStatus() == null) ? null : client.getStatus().toString();
            this.description = client.getDescription();
        }
    }

    @Data
    static class ClientCreateRequest {
        @NotBlank
        private String name;
        private String description;
    }

    @Data
    static class ClientUpdateRequest {
        @NotBlank
        private String name;
        @Min(3600)
        @Max(7200)
        private Long accessTokenValidityInSeconds;
        private String description;
    }

    @Data
    static class ClientStatusUpdateRequest {
        private ClientStatus clientStatus;
    }

    @Data
    static class ClientDeleteResponse {
        private final UUID deletedClientId;

        public ClientDeleteResponse(UUID id) {
            this.deletedClientId = id;
        }
    }
}
