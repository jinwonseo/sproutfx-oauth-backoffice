package kr.sproutfx.oauth.backoffice.api.member.service;

import kr.sproutfx.oauth.backoffice.api.member.entity.Member;
import kr.sproutfx.oauth.backoffice.api.member.exception.MemberNotFoundException;
import kr.sproutfx.oauth.backoffice.api.member.repository.MemberQueryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class MemberQueryService {
    private final MemberQueryRepository memberQueryRepository;

    public MemberQueryService(MemberQueryRepository memberQueryRepository) {
        this.memberQueryRepository = memberQueryRepository;
    }

    public List<Member> findAll() {
        return this.memberQueryRepository.findAll();
    }

    public Member findById(UUID id) {
        return this.memberQueryRepository.findById(id).orElseThrow(MemberNotFoundException::new);
    }

    public Member findByEmail(String email) {
        return this.memberQueryRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
    }
}
