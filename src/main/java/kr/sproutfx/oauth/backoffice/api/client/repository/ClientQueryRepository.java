package kr.sproutfx.oauth.backoffice.api.client.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.sproutfx.oauth.backoffice.api.client.entity.Client;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static kr.sproutfx.oauth.backoffice.api.client.entity.QClient.client;
import static kr.sproutfx.oauth.backoffice.api.project.entity.QProject.project;

@Repository
public class ClientQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public ClientQueryRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Optional<Client> findByIdWithProject(UUID id) {
        return Optional.ofNullable(
                jpaQueryFactory.selectFrom(client)
                        .leftJoin(client.project, project)
                        .fetchJoin()
                        .distinct()
                        .where(client.id.eq(id))
                        .fetchOne()
        );
    }

    public Optional<Client> findByCodeWithProject(String code) {
        return Optional.ofNullable(
                jpaQueryFactory.selectFrom(client)
                        .leftJoin(client.project, project)
                        .fetchJoin()
                        .distinct()
                        .where(client.code.eq(code))
                        .fetchOne()
        );
    }

    public Optional<Client> findBySecretWithProject(String secret) {
        return Optional.ofNullable(
                jpaQueryFactory.selectFrom(client)
                        .leftJoin(client.project)
                        .fetchJoin()
                        .distinct()
                        .where(client.secret.eq(secret))
                        .fetchOne()
        );
    }

    public List<Client> findAllWithProject() {
        return jpaQueryFactory.selectFrom(client)
                .leftJoin(client.project)
                .fetchJoin()
                .distinct()
                .fetch();
    }
}
