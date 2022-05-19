package kr.sproutfx.oauth.backoffice.api.client.repository.specification;

import kr.sproutfx.oauth.backoffice.api.client.entity.Client;
import org.springframework.data.jpa.domain.Specification;

public class ClientSpecification {
    private ClientSpecification() {
        throw new IllegalStateException();
    }

    public static Specification<Client> equalCode(String code) {
        return ((root, query, builder) -> builder.equal(root.get("code"), code));
    }

    public static Specification<Client> equalSecret(String secret) {
        return ((root, query, builder) -> builder.equal(root.get("secret"), secret));
    }
}
