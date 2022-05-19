package kr.sproutfx.oauth.backoffice.api.member.repository.specification;

import kr.sproutfx.oauth.backoffice.api.member.entity.Member;
import org.springframework.data.jpa.domain.Specification;

public class MemberSpecification {
    private MemberSpecification() {
        throw new IllegalStateException();
    }

    public static Specification<Member> equalEmail(String email) {
        return ((root, query, builder) -> builder.equal(root.get("email"), email));
    }
}
