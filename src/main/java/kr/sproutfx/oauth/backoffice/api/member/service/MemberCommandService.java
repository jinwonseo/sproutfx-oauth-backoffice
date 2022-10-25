package kr.sproutfx.oauth.backoffice.api.member.service;

import kr.sproutfx.oauth.backoffice.api.member.entity.Member;
import kr.sproutfx.oauth.backoffice.api.member.enumeration.MemberStatus;
import kr.sproutfx.oauth.backoffice.api.member.exception.MemberNotFoundException;
import kr.sproutfx.oauth.backoffice.api.member.repository.MemberCommandRepository;
import kr.sproutfx.oauth.backoffice.api.member.repository.MemberQueryRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class MemberCommandService {
    private final MemberCommandRepository memberCommandRepository;

    private final MemberQueryRepository memberQueryRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberCommandService(MemberCommandRepository memberCommandRepository, MemberQueryRepository memberQueryRepository, PasswordEncoder passwordEncoder) {
        this.memberCommandRepository = memberCommandRepository;
        this.memberQueryRepository = memberQueryRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UUID create(String email, String name, String password, String description) {
        Member persistenceMember = this.memberCommandRepository.save(Member.builder()
            .email(email)
            .name(name)
            .password(this.passwordEncoder.encode(password))
            .passwordExpired(LocalDateTime.now().plusDays(90)) // To-do: password 90일 하드코딩 password 정책관리 기능 추가 후 변수화
            .status(MemberStatus.PENDING_APPROVAL)
            .description(description)
            .build());

        return persistenceMember.getId();
    }

    public void update(UUID id, String email, String name, String description) {
        Member persistenceMember = this.memberCommandRepository.findById(id).orElseThrow(MemberNotFoundException::new);

        persistenceMember.update(email, name, description);
    }

    public UUID updatePassword(String email, String currentPassword, String newPassword) {
        Member persistenceMember = this.memberQueryRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

        persistenceMember.updatePassword(passwordEncoder, currentPassword, newPassword);

        return persistenceMember.getId();
    }

    public void updateStatus(UUID id, MemberStatus memberStatus) {
        Member persistenceMember = this.memberCommandRepository.findById(id).orElseThrow(MemberNotFoundException::new);

        persistenceMember.updateStatus(memberStatus);
    }

    public void deleteById(UUID id) {
        Member persistenceMember = this.memberCommandRepository.findById(id).orElseThrow(MemberNotFoundException::new);

        this.memberCommandRepository.delete(persistenceMember);
    }
}
