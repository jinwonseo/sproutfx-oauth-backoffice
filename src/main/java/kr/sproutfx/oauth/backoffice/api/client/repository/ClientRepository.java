package kr.sproutfx.oauth.backoffice.api.client.repository;

import kr.sproutfx.oauth.backoffice.api.client.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID>, JpaSpecificationExecutor<Client> {
    @Query(value = "SELECT distinct c FROM Client c left join fetch c.project p WHERE c.code = ?1")
    Optional<Client> findByCodeWithProject(String code);

    @Query(value = "SELECT distinct c FROM Client c left join fetch c.project p WHERE c.secret = ?1")
    Optional<Client> findBySecretWithProject(String secret);

    @Query(value = "SELECT distinct c FROM Client c left join fetch c.project p")
    List<Client> findAllWithProject();
}
