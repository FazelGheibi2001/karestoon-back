package com.airbyte.charity.project;

import com.airbyte.charity.common.ParentService;
import com.airbyte.charity.dto.ProjectDTO;
import com.airbyte.charity.model.Project;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class ProjectService extends ParentService<Project, ProjectRepository, ProjectDTO> {

    public ProjectService(ProjectRepository repository) {
        super(repository);
    }

    @Override
    public Project updateModelFromDto(Project project, ProjectDTO dto) {
        project.setTitle(dto.getTitle() != null ? dto.getTitle() : project.getTitle());
        project.setDescription(dto.getDescription() != null ? dto.getDescription() : project.getDescription());
        project.setStatus(dto.getStatus() != null ? dto.getStatus() : project.getStatus());
        project.setLikeCount(dto.getLikeCount() != null ? dto.getLikeCount() : project.getLikeCount());
        project.setExpectedBudge(dto.getExpectedBudge() != null ? dto.getExpectedBudge() : project.getExpectedBudge());
        project.setPrepareBudge(dto.getPrepareBudge() != null ? dto.getPrepareBudge() : project.getPrepareBudge());
        project.setStartDate(dto.getStartDate() != null ? dto.getStartDate() : project.getStartDate());
        project.setEndDate(dto.getEndDate() != null ? dto.getEndDate() : project.getEndDate());
        project.setProfileId(dto.getProfileId() != null ? dto.getProfileId() : project.getProfileId());
        project.setMotivationSentence(dto.getMotivationSentence() != null ? dto.getMotivationSentence() : project.getMotivationSentence());
        project.setPriority(dto.getPriority() != null ? dto.getPriority() : project.getPriority());
        return project;
    }

    @Override
    public Project convertDTO(ProjectDTO dto) {
        Project project = new Project();
        project.setTitle(dto.getTitle());
        project.setDescription(dto.getDescription());
        project.setStatus(dto.getStatus());
        project.setLikeCount(dto.getLikeCount());
        project.setExpectedBudge(dto.getExpectedBudge());
        project.setPrepareBudge(dto.getPrepareBudge());
        project.setStartDate(dto.getStartDate());
        project.setEndDate(dto.getEndDate());
        project.setProfileId(dto.getProfileId());
        project.setMotivationSentence(dto.getMotivationSentence());
        project.setPriority(dto.getPriority());

        if (dto.getFiles() != null && !dto.getFiles().isEmpty()) {
            Map<String, String> fileMap = new TreeMap<>();
            dto.getFiles().forEach(fileDTO -> fileMap.put(fileDTO.getName(), fileDTO.getFileId()));
            project.setFiles(fileMap);
        }

        return project;
    }

    @Override
    public List<Project> getWithSearch(ProjectDTO search) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> criteriaBuilderQuery = criteriaBuilder.createQuery(Project.class);

        Root<Project> root = criteriaBuilderQuery.from(Project.class);
        List<Predicate> predicates = new ArrayList<>();

        if (search.getTitle() != null && !search.getTitle().isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("title"), "%" + search.getTitle() + "%"));
        }
        if (search.getStatus() != null) {
            predicates.add(criteriaBuilder.equal(root.get("status"), search.getStatus()));
        }
        if (search.getPriority() != null) {
            predicates.add(criteriaBuilder.equal(root.get("priority"), search.getPriority()));
        }

        criteriaBuilderQuery.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(criteriaBuilderQuery).getResultList();
    }
}
