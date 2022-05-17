package kr.sproutfx.oauth.backoffice.api.member.controller;

import kr.sproutfx.oauth.backoffice.api.member.entity.Member;
import kr.sproutfx.oauth.backoffice.api.member.enumeration.MemberStatus;
import kr.sproutfx.oauth.backoffice.api.member.service.MemberCommandService;
import kr.sproutfx.oauth.backoffice.api.member.service.MemberQueryService;
import kr.sproutfx.oauth.backoffice.common.base.BaseController;
import kr.sproutfx.oauth.backoffice.common.exception.InvalidArgumentException;
import lombok.Data;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "/members")
public class MemberController extends BaseController {
    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    public MemberController(MemberCommandService memberCommandService, MemberQueryService memberQueryService) {
        this.memberCommandService = memberCommandService;
        this.memberQueryService = memberQueryService;
    }

    @GetMapping
    public StructuredBody<List<MemberResponse>> findAll() {

        return StructuredBody.content(
            this.memberQueryService.findAll().stream().map(MemberResponse::new).collect(toList()));
    }

    @GetMapping("/{id}")
    public StructuredBody<MemberResponse> findById(@PathVariable UUID id) {
        return StructuredBody.content(
            new MemberResponse(this.memberQueryService.findById(id)));
    }

    @PostMapping
    public StructuredBody<MemberResponse> create(@RequestBody @Validated MemberCreateRequest memberCreateRequest, Errors errors) {

        if (errors.hasErrors()) throw new InvalidArgumentException();

        UUID id = this.memberCommandService.create(memberCreateRequest.getEmail(), memberCreateRequest.getName(), memberCreateRequest.getPassword(), memberCreateRequest.getDescription());

        return StructuredBody.content(
            new MemberResponse(this.memberQueryService.findById(id)));
    }

    @PutMapping("/{id}")
    public StructuredBody<MemberResponse> update(@PathVariable UUID id, @RequestBody @Validated MemberUpdateRequest memberUpdateRequest, Errors errors) {

        if (errors.hasErrors()) throw new InvalidArgumentException();

        String email = memberUpdateRequest.getEmail();
        String name = memberUpdateRequest.getName();
        String description = memberUpdateRequest.getDescription();

        this.memberCommandService.update(id, email, name, description);

        return StructuredBody.content(
            new MemberResponse(this.memberQueryService.findById(id)));
    }

    @PatchMapping(value = "/{id}/status")
    public StructuredBody<MemberResponse> updateStatus(@PathVariable UUID id, @RequestBody @Validated MemberStatusUpdateRequest memberStatusUpdateRequest, Errors errors) {

        if (errors.hasErrors()) throw new InvalidArgumentException();

        MemberStatus memberStatus = memberStatusUpdateRequest.getMemberStatus();

        this.memberCommandService.updateStatus(id, memberStatus);

        return StructuredBody.content(
            new MemberResponse(this.memberQueryService.findById(id)));
    }

    @PatchMapping(value = "/{email}/password")
    public StructuredBody<MemberResponse> updatePassword(@PathVariable String email, @RequestBody @Validated MemberPasswordUpdateRequest memberPasswordUpdateRequest, Errors errors) {

        if (errors.hasErrors()) throw new InvalidArgumentException();

        String currentPassword = memberPasswordUpdateRequest.getCurrentPassword();
        String newPassword = memberPasswordUpdateRequest.getNewPassword();

        UUID id = this.memberCommandService.updatePassword(email, currentPassword, newPassword);

        return StructuredBody.content(
            new MemberResponse(this.memberQueryService.findById(id)));
    }

    @DeleteMapping("/{id}")
    public StructuredBody<MemberDeleteResponse> delete(@PathVariable UUID id) {

        this.memberCommandService.deleteById(id);

        return StructuredBody.content(
            new MemberDeleteResponse(id));
    }

    @Data
    static class MemberCreateRequest {
        @Email
        private String email;
        @NotBlank
        private String name;
        @NotBlank
        private String password;
        private String description;
    }

    @Data
    static class MemberUpdateRequest {
        @Email
        private String email;
        @NotBlank
        private String name;
        private String description;
    }

    @Data
    static class MemberPasswordUpdateRequest {
        private String currentPassword;
        private String newPassword;
    }

    @Data
    static class MemberStatusUpdateRequest {
        private MemberStatus memberStatus;
    }

    @Data
    static class MemberResponse {
        private final UUID id;
        private final String email;
        private final String name;
        private final LocalDateTime passwordExpired;
        private final String status;
        private final String description;

        public MemberResponse(Member member) {
            this.id = member.getId();
            this.email = member.getEmail();
            this.name = member.getName();
            this.passwordExpired = member.getPasswordExpired();
            this.status = (member.getStatus() == null) ? null : member.getStatus().toString();
            this.description = member.getDescription();
        }
    }

    @Data
    static class MemberDeleteResponse {
        private UUID deletedMemberId;

        public MemberDeleteResponse(UUID id) {
            this.deletedMemberId = id;
        }
    }
}
