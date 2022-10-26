package kr.sproutfx.oauth.backoffice.api.client.controller;

import kr.sproutfx.oauth.backoffice.api.client.dto.request.ClientCreateRequest;
import kr.sproutfx.oauth.backoffice.api.client.dto.request.ClientStatusUpdateRequest;
import kr.sproutfx.oauth.backoffice.api.client.dto.request.ClientUpdateRequest;
import kr.sproutfx.oauth.backoffice.api.client.dto.response.ClientResponse;
import kr.sproutfx.oauth.backoffice.api.client.service.ClientCommandService;
import kr.sproutfx.oauth.backoffice.api.client.service.ClientQueryService;
import kr.sproutfx.oauth.backoffice.common.exception.InvalidArgumentException;
import kr.sproutfx.oauth.backoffice.common.response.entity.StructuredResponseEntity;
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

import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = ClientController.REQUEST_PATH)
public class ClientController {
    public static final String REQUEST_PATH = "/clients";
    private final ClientCommandService clientCommandService;
    private final ClientQueryService clientQueryService;

    public ClientController(ClientCommandService clientCommandService, ClientQueryService clientQueryService) {
        this.clientCommandService = clientCommandService;
        this.clientQueryService = clientQueryService;
    }

    @GetMapping
    public StructuredResponseEntity findAll() {
        return StructuredResponseEntity.succeeded(
                this.clientQueryService.findAll()
                        .stream()
                        .map(ClientResponse::new)
                        .collect(toList())
        );
    }

    @GetMapping(value = "/{id}")
    public StructuredResponseEntity findById(@PathVariable("id") UUID id) {
        return StructuredResponseEntity.succeeded(
                new ClientResponse(this.clientQueryService.findById(id))
        );
    }

    @PostMapping
    public StructuredResponseEntity create(@RequestBody @Validated ClientCreateRequest clientCreateRequest, UriComponentsBuilder uriComponentsBuilder, Errors errors) {

        if (errors.hasErrors()) throw new InvalidArgumentException();

        UUID id = this.clientCommandService.create(clientCreateRequest.getName(), clientCreateRequest.getDescription());

        return StructuredResponseEntity.created(
                uriComponentsBuilder.path(String.format("%s/%s", REQUEST_PATH, id)).build().toUri(),
                new ClientResponse(this.clientQueryService.findById(id))
        );
    }

    @PutMapping(value = "/{id}")
    public StructuredResponseEntity update(@PathVariable UUID id, @RequestBody @Validated ClientUpdateRequest clientUpdateRequest, Errors errors) {

        if (errors.hasErrors()) throw new InvalidArgumentException();

        String name = clientUpdateRequest.getName();
        String description = clientUpdateRequest.getDescription();

        this.clientCommandService.update(id, name, description);

        return StructuredResponseEntity.succeeded(
                new ClientResponse(this.clientQueryService.findById(id))
        );
    }

    @PatchMapping("/{id}/status")
    public StructuredResponseEntity updateStatus(@PathVariable UUID id, @RequestBody ClientStatusUpdateRequest clientStatusUpdateRequest) {

        this.clientCommandService.updateStatus(id, clientStatusUpdateRequest.getClientStatus());

        return StructuredResponseEntity.succeeded(
                new ClientResponse(this.clientQueryService.findById(id))
        );
    }

    @DeleteMapping(value = "/{id}")
    public StructuredResponseEntity delete(@PathVariable UUID id) {

        this.clientCommandService.deleteById(id);

        return StructuredResponseEntity.deleted();
    }
}