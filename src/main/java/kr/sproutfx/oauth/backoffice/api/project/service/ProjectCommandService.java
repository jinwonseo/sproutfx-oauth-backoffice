package kr.sproutfx.oauth.backoffice.api.project.service;

import kr.sproutfx.oauth.backoffice.api.project.entity.Project;
import kr.sproutfx.oauth.backoffice.api.project.enumeration.ProjectStatus;
import kr.sproutfx.oauth.backoffice.api.project.exception.ProjectNotFoundException;
import kr.sproutfx.oauth.backoffice.api.project.repository.ProjectCommandRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class ProjectCommandService {
    private final ProjectCommandRepository projectCommandRepository;

    public ProjectCommandService(ProjectCommandRepository projectCommandRepository) {
        this.projectCommandRepository = projectCommandRepository;
    }

    public UUID create(String name, String description) {
        Project persistenceProject = this.projectCommandRepository.save(Project.builder().name(name).description(description).status(ProjectStatus.PENDING_APPROVAL).build());

        return persistenceProject.getId();
    }

    public void update(UUID id, String name, String description) {
        Project persistenceProject = this.projectCommandRepository.findById(id).orElseThrow(ProjectNotFoundException::new);

        persistenceProject.update(name, description);
    }

    public void updateStatus(UUID id, ProjectStatus projectStatus) {
        Project persistenceProject = this.projectCommandRepository.findById(id).orElseThrow(ProjectNotFoundException::new);

        persistenceProject.updateStatus(projectStatus);
    }

    public void delete(UUID id) {
        Project persistenceProject = this.projectCommandRepository.findById(id).orElseThrow(ProjectNotFoundException::new);

        this.projectCommandRepository.delete(persistenceProject);
    }
}
