package com.airbyte.charity.user;

import com.airbyte.charity.common.ParentService;
import com.airbyte.charity.dto.UserDTO;
import com.airbyte.charity.model.UserInformation;
import com.airbyte.charity.permission.Role;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserInformationService extends ParentService<UserInformation, UserInformationRepository, UserDTO> {

    public UserInformationService(UserInformationRepository repository) {
        super(repository);
    }

    @Override
    public UserInformation updateModelFromDto(UserInformation user, UserDTO dto) {
        user.setFirstName(dto.getFirstName() != null ? dto.getFirstName() : user.getFirstName());
        user.setLastName(dto.getLastName() != null ? dto.getLastName() : user.getLastName());
        user.setPassword(dto.getPassword() != null ? dto.getPassword() : user.getPassword());
        return user;
    }

    @Override
    public UserInformation convertDTO(UserDTO dto) {
        UserInformation user = new UserInformation();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setRole(dto.getRole() != null ? Role.valueOf(dto.getRole()) : null);
        user.setPassword(dto.getPassword());
        user.setUsername(dto.getUsername());
        return user;
    }

    @Override
    protected void preSave(UserDTO dto) {
        repository.findAll()
                .forEach(user -> {
                    if (dto.getUsername() != null && user.getUsername().equals(dto.getUsername())) {
                        throw new IllegalArgumentException("username exists!!!");
                    }
                });
    }

    @Override
    public List<UserInformation> getWithSearch(UserDTO search) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserInformation> criteriaBuilderQuery = criteriaBuilder.createQuery(UserInformation.class);

        Root<UserInformation> root = criteriaBuilderQuery.from(UserInformation.class);
        List<Predicate> predicates = new ArrayList<>();

        if (search.getRole() != null) {
            predicates.add(criteriaBuilder.equal(root.get("role"), search.getRole()));
        }
        if (search.getUsername() != null) {
            predicates.add(criteriaBuilder.equal(root.get("username"), search.getUsername()));
        }

        criteriaBuilderQuery.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(criteriaBuilderQuery).getResultList();
    }

    public UserInformation getByUsername(String username) {
        UserDTO dto = new UserDTO();
        dto.setUsername(username);
        List<UserInformation> search = this.getWithSearch(dto);

        if (search == null || search.isEmpty()) {
            throw new IllegalArgumentException("can not find this user!!!");
        }

        return search.get(0);
    }
}
