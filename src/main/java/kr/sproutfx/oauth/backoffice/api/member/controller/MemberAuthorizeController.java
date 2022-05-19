package kr.sproutfx.oauth.backoffice.api.member.controller;

import kr.sproutfx.oauth.backoffice.api.member.entity.Member;
import kr.sproutfx.oauth.backoffice.api.member.service.MemberQueryService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping(value = "/de7e284c-38ef-46fb-b911-12ad2faf8623/members")
public class MemberAuthorizeController {
    private final MemberQueryService memberQueryService;

    public MemberAuthorizeController(MemberQueryService memberQueryService) {
        this.memberQueryService = memberQueryService;
    }

    @GetMapping(value = "/{id}")
    public MemberResponse findById(@RequestHeader("provider-code") String providerCode, @PathVariable UUID id) {
        return new MemberResponse(this.memberQueryService.findById(id));
    }

    @GetMapping(params = {"email"})
    public MemberResponse findByEmail(@RequestHeader("provider-code") String providerCode, @RequestParam String email) {
        return new MemberResponse(this.memberQueryService.findByEmail(email));
    }

    @Data
    private static class MemberResponse {
        private final UUID id;
        private final String email;
        private final String name;
        private final String password;
        private final LocalDateTime passwordExpired;
        private final String status;
        private final String description;

        public MemberResponse(Member member) {
            this.id = member.getId();
            this.email = member.getEmail();
            this.name = member.getName();
            this.password = member.getPassword();
            this.passwordExpired = member.getPasswordExpired();
            this.status = (member.getStatus() == null) ? null : member.getStatus().toString();
            this.description = member.getDescription();
        }
    }
}
