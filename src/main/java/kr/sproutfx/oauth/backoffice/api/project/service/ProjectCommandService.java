package kr.sproutfx.oauth.backoffice.api.project.service;

import kr.sproutfx.oauth.backoffice.api.project.entity.Project;
import kr.sproutfx.oauth.backoffice.api.project.enumeration.ProjectStatus;
import kr.sproutfx.oauth.backoffice.api.project.exception.ProjectNotFoundException;
import kr.sproutfx.oauth.backoffice.api.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class ProjectCommandService {
    private final ProjectRepository projectRepository;

    public ProjectCommandService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public UUID create(String name, String description) {
        Project persistenceProject = this.projectRepository.save(Project.builder().name(name).description(description).status(ProjectStatus.PENDING_APPROVAL).build());

        return persistenceProject.getId();
    }

    public void update(UUID id, String name, String description) {
        Project persistenceProject = this.projectRepository.findById(id).orElseThrow(ProjectNotFoundException::new);

        persistenceProject.setName(name);
        persistenceProject.setDescription(description);
    }

    public void updateStatus(UUID id, ProjectStatus projectStatus) {
        Project persistenceProject = this.projectRepository.findById(id).orElseThrow(ProjectNotFoundException::new);

        persistenceProject.setStatus(projectStatus);
    }

    public void delete(UUID id) {
        Project persistenceProject = this.projectRepository.findById(id).orElseThrow(ProjectNotFoundException::new);

        this.projectRepository.delete(persistenceProject);
    }
}
