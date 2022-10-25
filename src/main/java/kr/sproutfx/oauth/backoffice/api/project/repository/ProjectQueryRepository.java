package kr.sproutfx.oauth.backoffice.api.project.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.sproutfx.oauth.backoffice.api.project.entity.Project;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static kr.sproutfx.oauth.backoffice.api.client.entity.QClient.client;
import static kr.sproutfx.oauth.backoffice.api.project.entity.QProject.project;

@Repository
public class ProjectQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public ProjectQueryRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Project> findAllWithClients() {
        return jpaQueryFactory.selectFrom(project)
                .join(project.clients, client)
                .fetchJoin()
                .distinct()
                .fetch();
    }

    public Optional<Project> findByIdWithClients(UUID id) {
        return Optional.ofNullable(
            jpaQueryFactory.selectFrom(project)
                    .join(project.clients, client)
                    .fetchJoin()
                    .distinct()
                    .where(project.id.eq(id))
                    .fetchOne()
        );
    }
}
