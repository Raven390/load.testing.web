package ru.develonica.load.testing.web.test.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.develonica.load.testing.web.test.model.exception.EntityNotFoundException;

import java.util.List;
import java.util.UUID;

public interface CrudRepository<T, I> {

    List<T> getAllWhereUserId(UUID userId);

    T getById(UUID id) throws EntityNotFoundException;

    T save(T entity, UUID userId) throws JsonProcessingException;

    I delete(I id) throws EntityNotFoundException;
}
