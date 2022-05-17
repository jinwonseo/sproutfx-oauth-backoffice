package kr.sproutfx.oauth.backoffice.api.project.service;

import kr.sproutfx.oauth.backoffice.api.project.entity.Project;
import kr.sproutfx.oauth.backoffice.api.project.exception.ProjectNotFoundException;
import kr.sproutfx.oauth.backoffice.api.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ProjectQueryService {
    private final ProjectRepository projectRepository;

    public ProjectQueryService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> findAll() {
        return this.projectRepository.findAll();
    }

    public List<Project> findAllWithClients() {
        return this.projectRepository.findAllWithClients();
    }

    public Project findById(UUID id) {
        return this.projectRepository.findByIdWithClients(id).orElseThrow(ProjectNotFoundException::new);
    }
}
