package kr.sproutfx.oauth.backoffice.api.member.controller;

import kr.sproutfx.oauth.backoffice.api.member.dto.response.MemberResponse;
import kr.sproutfx.oauth.backoffice.api.member.service.MemberQueryService;
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
@RequestMapping(value = MemberAuthorizeController.REQUEST_PATH)
public class MemberAuthorizeController {
    public static final String REQUEST_PATH = "/resource-servers/{resource-server-id}/members";

    private final ResourceServerProperties resourceServerProperties;
    private final MemberQueryService memberQueryService;

    public MemberAuthorizeController(ResourceServerProperties resourceServerProperties, MemberQueryService memberQueryService) {
        this.resourceServerProperties = resourceServerProperties;
        this.memberQueryService = memberQueryService;
    }

    @GetMapping(value = "/{id}")
    public StructuredResponseEntity findById(@PathVariable(value = "resource-server-id") UUID resourceServerId, @PathVariable UUID id) {

        if (!resourceServerProperties.getId().equals(resourceServerId)) throw new InvalidResourceServerId();

        return StructuredResponseEntity.succeeded(
                new MemberResponse(this.memberQueryService.findById(id))
        );
    }

    @GetMapping(params = {"email"})
    public StructuredResponseEntity findByEmail(@PathVariable(value = "resource-server-id") UUID resourceServerId, @RequestParam String email) {

        if (!resourceServerProperties.getId().equals(resourceServerId)) throw new InvalidResourceServerId();

        return StructuredResponseEntity.succeeded(
                new MemberResponse(this.memberQueryService.findByEmail(email))
        );
    }
}
