package kr.sproutfx.oauth.backoffice.api.project.repository;

import kr.sproutfx.oauth.backoffice.api.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProjectCommandRepository extends JpaRepository<Project, UUID> {

}
