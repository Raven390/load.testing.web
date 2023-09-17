package ru.develonica.load.testing.web.test.repository;

import ru.develonica.load.testing.web.test.model.exception.EntityNotFoundException;

import java.util.List;
import java.util.UUID;

public interface CrudRepository<T, I> {

    List<T> getAllWhereUserId(UUID userId);

    T getById(UUID id);

    T save(T entity);

    I delete(I id) throws EntityNotFoundException;
}
