package kr.sproutfx.oauth.backoffice.api.member.controller;

import kr.sproutfx.oauth.backoffice.api.member.dto.response.MemberResponse;
import kr.sproutfx.oauth.backoffice.api.member.service.MemberQueryService;
import kr.sproutfx.oauth.backoffice.common.response.entity.StructuredResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = MemberAuthorizeController.REQUEST_PATH)
public class MemberAuthorizeController {
    public static final String REQUEST_PATH = "/de7e284c-38ef-46fb-b911-12ad2faf8623/members";
    private final MemberQueryService memberQueryService;

    public MemberAuthorizeController(MemberQueryService memberQueryService) {
        this.memberQueryService = memberQueryService;
    }

    @GetMapping(value = "/{id}")
    public StructuredResponseEntity findById(@PathVariable UUID id) {
        return StructuredResponseEntity.succeeded(
                new MemberResponse(this.memberQueryService.findById(id))
        );
    }

    @GetMapping(params = {"email"})
    public StructuredResponseEntity findByEmail(@RequestParam String email) {
        return StructuredResponseEntity.succeeded(
                new MemberResponse(this.memberQueryService.findByEmail(email))
        );
    }
}
