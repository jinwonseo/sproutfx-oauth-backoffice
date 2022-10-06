package kr.sproutfx.oauth.backoffice.api.client.controller;

import kr.sproutfx.oauth.backoffice.api.client.entity.Client;
import kr.sproutfx.oauth.backoffice.api.client.enumeration.ClientStatus;
import kr.sproutfx.oauth.backoffice.api.client.service.ClientCommandService;
import kr.sproutfx.oauth.backoffice.api.client.service.ClientQueryService;
import kr.sproutfx.oauth.backoffice.common.dto.StructuredBody;
import kr.sproutfx.oauth.backoffice.common.exception.InvalidArgumentException;
import lombok.Data;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientCommandService clientCommandService;
    private final ClientQueryService clientQueryService;

    public ClientController(ClientCommandService clientCommandService, ClientQueryService clientQueryService) {
        this.clientCommandService = clientCommandService;
        this.clientQueryService = clientQueryService;
    }

    @GetMapping
    public StructuredBody findAll() {

        return StructuredBody.content(this.clientQueryService.findAll().stream().map(ClientResponse::new).collect(toList()));
    }

    @GetMapping(value = "/{id}")
    public StructuredBody findById(@PathVariable("id") UUID id) {

        return StructuredBody.content(new ClientResponse(this.clientQueryService.findById(id)));
    }

    @PostMapping
    public StructuredBody create(@RequestBody @Validated ClientCreateRequest clientCreateRequest, Errors errors) {

        if (errors.hasErrors()) throw new InvalidArgumentException();

        UUID id = this.clientCommandService.create(clientCreateRequest.getName(), clientCreateRequest.getDescription());

        return StructuredBody.content(new ClientResponse(this.clientQueryService.findById(id)));
    }

    @PutMapping(value = "/{id}")
    public StructuredBody update(@PathVariable UUID id, @RequestBody @Validated ClientUpdateRequest clientUpdateRequest, Errors errors) {

        if (errors.hasErrors()) throw new InvalidArgumentException();

        String name = clientUpdateRequest.getName();
        String description = clientUpdateRequest.getDescription();

        this.clientCommandService.update(id, name, description);

        return StructuredBody.content(new ClientResponse(this.clientQueryService.findById(id)));
    }

    @PatchMapping("/{id}/status")
    public StructuredBody updateStatus(@PathVariable UUID id, @RequestBody ClientStatusUpdateRequest clientStatusUpdateRequest) {

        this.clientCommandService.updateStatus(id, clientStatusUpdateRequest.getClientStatus());

        return StructuredBody.content(new ClientResponse(this.clientQueryService.findById(id)));
    }

    @DeleteMapping(value = "/{id}")
    public StructuredBody delete(@PathVariable UUID id) {

        this.clientCommandService.deleteById(id);

        return StructuredBody.content(new ClientDeleteResponse(id));
    }

    @Data
    private static class ClientCreateRequest {
        @NotBlank
        private String name;
        private String description;
    }

    @Data
    private static class ClientUpdateRequest {
        @NotBlank
        private String name;
        private String description;
    }

    @Data
    private static class ClientStatusUpdateRequest {
        @NotBlank
        private ClientStatus clientStatus;
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
            this.status = (client.getStatus() == null) ? null : client.getStatus().toString();
            this.description = client.getDescription();
        }
    }

    @Data
    private static class ClientDeleteResponse {
        private final UUID deletedClientId;

        public ClientDeleteResponse(UUID id) {
            this.deletedClientId = id;
        }
    }
}