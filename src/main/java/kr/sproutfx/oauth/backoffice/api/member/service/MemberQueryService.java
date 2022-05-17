package kr.sproutfx.oauth.backoffice.api.member.service;

import kr.sproutfx.oauth.backoffice.api.member.entity.Member;
import kr.sproutfx.oauth.backoffice.api.member.exception.MemberNotFoundException;
import kr.sproutfx.oauth.backoffice.api.member.repository.MemberRepository;
import kr.sproutfx.oauth.backoffice.api.member.repository.specification.MemberSpecification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class MemberQueryService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberQueryService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Member> findAll() {
        return this.memberRepository.findAll();
    }

    public Member findById(UUID id) {
        return this.memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
    }

    public Member findByEmail(String email) {
        return this.memberRepository.findOne(MemberSpecification.equalEmail(email)).orElseThrow(MemberNotFoundException::new);
    }
}
