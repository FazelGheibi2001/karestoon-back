package com.airbyte.charity.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public abstract class ParentService<MODEL, REPOSITORY extends JpaRepository<MODEL, String>, DTO> {
    protected final REPOSITORY repository;
    @PersistenceContext
    protected EntityManager entityManager;


    public ParentService(REPOSITORY repository) {
        this.repository = repository;
    }

    public MODEL save(DTO dto) {
        MODEL model = convertDTO(dto);
        preSave(dto);
        model = repository.save(model);
        postSave(model, dto);
        return model;
    }

    protected void preSave(DTO dto) {
    }

    protected void postSave(MODEL model, DTO dto) {
    }

    public MODEL getOne(String id) {
        Optional<MODEL> model = repository.findById(id);
        if (model.isPresent()) {
            return model.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "model with this id not found");
        }
    }

    public List<MODEL> getAll() {
        return repository.findAll();
    }

    public MODEL update(String id, DTO dto) {
        MODEL model = getOne(id);
        model = updateModelFromDto(model, dto);
        model = repository.save(model);
        postUpdate(model, dto);
        return model;
    }

    protected void postUpdate(MODEL model, DTO dto) {
    }

    public void delete(String id) {
        if (repository.findById(id).isPresent()) {
            preDelete(repository.findById(id).get());
            repository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "model with this id not found");
        }
    }

    protected void preDelete(MODEL model) {
    }

    public abstract MODEL updateModelFromDto(MODEL model, DTO dto);

    public abstract MODEL convertDTO(DTO dto);

    public abstract List<MODEL> getWithSearch(DTO search);

    public void deleteAllById(List<String> ids) {
        if (ids.size() > 0) {
            repository.deleteAllById(ids);
        }
    }
}
