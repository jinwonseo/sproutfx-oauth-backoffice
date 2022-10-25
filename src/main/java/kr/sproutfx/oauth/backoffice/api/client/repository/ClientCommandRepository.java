package kr.sproutfx.oauth.backoffice.api.client.repository;

import kr.sproutfx.oauth.backoffice.api.client.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientCommandRepository extends JpaRepository<Client, UUID> {

}
