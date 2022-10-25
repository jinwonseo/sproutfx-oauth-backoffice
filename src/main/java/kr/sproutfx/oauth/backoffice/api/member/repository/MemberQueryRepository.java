package kr.sproutfx.oauth.backoffice.api.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.sproutfx.oauth.backoffice.api.member.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static kr.sproutfx.oauth.backoffice.api.member.entity.QMember.member;

@Repository
public class MemberQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public MemberQueryRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Member> findAll() {
        return this.jpaQueryFactory.selectFrom(member)
                .fetch();
    }

    public Optional<Member> findByEmail(String email) {
        return Optional.ofNullable(
            this.jpaQueryFactory.selectFrom(member)
                    .where(member.email.eq(email))
                    .fetchOne()
        );
    }

    public Optional<Member> findById(UUID id) {
        return Optional.ofNullable(
            this.jpaQueryFactory.selectFrom(member)
                    .where(member.id.eq(id))
                    .fetchOne()
        );
    }
}
