package kr.sproutfx.oauth.backoffice.api.project.service;

import kr.sproutfx.oauth.backoffice.api.project.entity.Project;
import kr.sproutfx.oauth.backoffice.api.project.exception.ProjectNotFoundException;
import kr.sproutfx.oauth.backoffice.api.project.repository.ProjectQueryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ProjectQueryService {
    private final ProjectQueryRepository projectQueryRepository;

    public ProjectQueryService(ProjectQueryRepository projectQueryRepository) {
        this.projectQueryRepository = projectQueryRepository;
    }

    public List<Project> findAll() {
        return this.projectQueryRepository.findAllWithClients();
    }

    public List<Project> findAllWithClients() {
        return this.projectQueryRepository.findAllWithClients();
    }

    public Project findById(UUID id) {
        return this.projectQueryRepository.findByIdWithClients(id).orElseThrow(ProjectNotFoundException::new);
    }
}
