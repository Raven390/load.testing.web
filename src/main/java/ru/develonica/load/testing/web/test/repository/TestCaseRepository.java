package ru.develonica.load.testing.web.test.repository;

import org.springframework.stereotype.Repository;
import ru.develonica.load.testing.web.test.model.exception.EntityNotFoundException;
import ru.develonica.load.testing.web.test.model.object.TestCase;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Repository
public class TestCaseRepository implements CrudRepository<TestCase, UUID> {
    @Override
    public List<TestCase> getAllWhereUserId(UUID userId) {
        return Collections.emptyList();
    }

    @Override
    public TestCase getById(UUID id) {
        return null;
    }

    @Override
    public TestCase save(TestCase entity) {
        return null;
    }

    @Override
    public UUID delete(UUID id) throws EntityNotFoundException {
        return UUID.randomUUID();
    }
}
