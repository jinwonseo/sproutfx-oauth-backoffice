package kr.sproutfx.oauth.backoffice.api.member.repository;

import kr.sproutfx.oauth.backoffice.api.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MemberCommandRepository extends JpaRepository<Member, UUID> {

}
