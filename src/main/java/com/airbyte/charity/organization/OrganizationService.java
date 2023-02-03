package com.airbyte.charity.organization;

import com.airbyte.charity.common.ParentService;
import com.airbyte.charity.dto.OrganizationDTO;
import com.airbyte.charity.model.Organization;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrganizationService extends ParentService<Organization, OrganizationRepository, OrganizationDTO> {

    public OrganizationService(OrganizationRepository repository) {
        super(repository);
    }

    @Override
    public Organization updateModelFromDto(Organization organization, OrganizationDTO dto) {
        organization.setAddress(dto.getAddress() != null ? dto.getAddress() : organization.getAddress());
        organization.setDescription(dto.getDescription() != null ? dto.getDescription() : organization.getDescription());
        organization.setName(dto.getName() != null ? dto.getName() : organization.getName());
        return organization;
    }

    @Override
    public Organization convertDTO(OrganizationDTO dto) {
        Organization organization = new Organization();
        organization.setAddress(dto.getAddress());
        organization.setDescription(dto.getDescription());
        organization.setName(dto.getName());
        return organization;
    }

    @Override
    public List<Organization> getWithSearch(OrganizationDTO search) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Organization> criteriaBuilderQuery = criteriaBuilder.createQuery(Organization.class);

        Root<Organization> root = criteriaBuilderQuery.from(Organization.class);
        List<Predicate> predicates = new ArrayList<>();

        if (search.getName() != null && !search.getName().isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + search.getName() + "%"));
        }
        if (search.getProjectId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("projectId"), search.getName()));
        }

        criteriaBuilderQuery.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(criteriaBuilderQuery).getResultList();
    }
}
