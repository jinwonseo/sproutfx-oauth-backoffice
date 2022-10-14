package kr.sproutfx.oauth.backoffice.api.member.controller;

import kr.sproutfx.oauth.backoffice.api.member.entity.Member;
import kr.sproutfx.oauth.backoffice.api.member.enumeration.MemberStatus;
import kr.sproutfx.oauth.backoffice.api.member.service.MemberCommandService;
import kr.sproutfx.oauth.backoffice.api.member.service.MemberQueryService;
import kr.sproutfx.oauth.backoffice.common.dto.response.BaseResponse;
import kr.sproutfx.oauth.backoffice.common.dto.response.StructuredResponseEntity;
import kr.sproutfx.oauth.backoffice.common.exception.InvalidArgumentException;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
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

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = MemberController.REQUEST_PATH)
public class MemberController {
    public static final String REQUEST_PATH = "/members";
    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    public MemberController(MemberCommandService memberCommandService, MemberQueryService memberQueryService) {
        this.memberCommandService = memberCommandService;
        this.memberQueryService = memberQueryService;
    }

    @GetMapping
    public StructuredResponseEntity findAll() {
        return StructuredResponseEntity.succeeded(this.memberQueryService.findAll().stream().map(MemberResponse::new).collect(toList()));
    }

    @GetMapping("/{id}")
    public StructuredResponseEntity findById(@PathVariable UUID id) {
        return StructuredResponseEntity.succeeded(new MemberResponse(this.memberQueryService.findById(id)));
    }

    @PostMapping
    public StructuredResponseEntity create(@RequestBody @Validated MemberCreateRequest memberCreateRequest, UriComponentsBuilder uriComponentsBuilder, Errors errors) {

        if (errors.hasErrors()) throw new InvalidArgumentException();

        UUID id = this.memberCommandService.create(memberCreateRequest.getEmail(), memberCreateRequest.getName(), memberCreateRequest.getPassword(), memberCreateRequest.getDescription());

        return StructuredResponseEntity.created(uriComponentsBuilder.path(String.format("%s/%s", REQUEST_PATH, id)).build().toUri(), new MemberResponse(this.memberQueryService.findById(id)));
    }

    @PutMapping("/{id}")
    public StructuredResponseEntity update(@PathVariable UUID id, @RequestBody @Validated MemberUpdateRequest memberUpdateRequest, Errors errors) {

        if (errors.hasErrors()) throw new InvalidArgumentException();

        String email = memberUpdateRequest.getEmail();
        String name = memberUpdateRequest.getName();
        String description = memberUpdateRequest.getDescription();

        this.memberCommandService.update(id, email, name, description);

        return StructuredResponseEntity.succeeded(new MemberResponse(this.memberQueryService.findById(id)));
    }

    @PatchMapping(value = "/{id}/status")
    public StructuredResponseEntity updateStatus(@PathVariable UUID id, @RequestBody @Validated MemberStatusUpdateRequest memberStatusUpdateRequest, Errors errors) {

        if (errors.hasErrors()) throw new InvalidArgumentException();

        MemberStatus memberStatus = memberStatusUpdateRequest.getMemberStatus();

        this.memberCommandService.updateStatus(id, memberStatus);

        return StructuredResponseEntity.succeeded(new MemberResponse(this.memberQueryService.findById(id)));
    }

    @PatchMapping(value = "/{email}/password")
    public StructuredResponseEntity updatePassword(@PathVariable String email, @RequestBody @Validated MemberPasswordUpdateRequest memberPasswordUpdateRequest, Errors errors) {

        if (errors.hasErrors()) throw new InvalidArgumentException();

        String currentPassword = memberPasswordUpdateRequest.getCurrentPassword();
        String newPassword = memberPasswordUpdateRequest.getNewPassword();

        UUID id = this.memberCommandService.updatePassword(email, currentPassword, newPassword);

        return StructuredResponseEntity.succeeded(new MemberResponse(this.memberQueryService.findById(id)));
    }

    @DeleteMapping("/{id}")
    public StructuredResponseEntity delete(@PathVariable UUID id) {

        this.memberCommandService.deleteById(id);

        return StructuredResponseEntity.deleted();
    }

    @Data
    private static class MemberCreateRequest {
        @Email
        private String email;
        @NotBlank
        private String name;
        @NotBlank
        private String password;
        private String description;
    }

    @Data
    private static class MemberUpdateRequest {
        @Email
        private String email;
        @NotBlank
        private String name;
        private String description;
    }

    @Data
    private static class MemberPasswordUpdateRequest {
        private String currentPassword;
        private String newPassword;
    }

    @Data
    private static class MemberStatusUpdateRequest {
        private MemberStatus memberStatus;
    }

    @Getter @Setter
    private static class MemberResponse extends BaseResponse {
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
}
