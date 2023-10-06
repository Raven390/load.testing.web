package ru.develonica.load.testing.web.test.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.develonica.load.testing.web.test.model.exception.EntityNotFoundException;
import ru.develonica.load.testing.web.test.model.object.TestCase;
import ru.develonica.load.testing.web.test.model.request.TestCaseRequest;

import java.util.List;
import java.util.UUID;

@Repository
public class TestCaseRepository implements CrudRepository<TestCase, UUID> {
    private static final Logger LOG = LoggerFactory.getLogger(TestCaseRepository.class);
    private static final String FIELD_ID = "id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_TEST_CASE_REQUEST = "test_case_request";

    private static final String UPSERT_TEST_CASE = """
            INSERT INTO test_app.test_case(id, name, test_case_request, user_id)
            VALUES(?,?,?::json,?)
            ON CONFLICT (id) DO UPDATE
            SET
                name = excluded.name,
                test_case_request = excluded.test_case_request,
                user_id = excluded.user_id;
            """;

    private static final String GET_ALL_WHERE_USER_ID = """
                SELECT tc.id, tc.name, tc.test_case_request
                FROM test_app.test_case AS tc
                INNER JOIN public.user_entity AS ue
                ON ue.id = ?
            """;

    private static final String DELETE_BY_ID = """
                DELETE FROM test_app.test_case
                WHERE id = ?
                RETURNING id;
            """;

    private static final String GET_BY_ID = """
                SELECT id, name, test_case_request, user_id
                FROM test_app.test_case
                WHERE id = ?
            """;

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final RowMapper<TestCase> testCaseRowMapper = (rs, rowNum) -> {
        TestCase testCase = new TestCase();
        testCase.setId(rs.getObject(FIELD_ID, UUID.class));
        testCase.setName(rs.getString(FIELD_NAME));
        try {
            testCase.setTestCaseRequest(objectMapper.readValue(rs.getString(FIELD_TEST_CASE_REQUEST), TestCaseRequest.class));
        } catch (JsonProcessingException e) {
            LOG.error("Error parsing test case request: {}", e.getMessage());
        }
        return testCase;
    };

    public TestCaseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TestCase> getAllWhereUserId(UUID userId) {
        return jdbcTemplate.query(GET_ALL_WHERE_USER_ID, testCaseRowMapper, userId.toString());
    }

    @Override
    public TestCase getById(UUID id) throws EntityNotFoundException {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, testCaseRowMapper, id);
        } catch (EmptyResultDataAccessException erdae) {
            throw new EntityNotFoundException("Test case with ID " + id + " not found", erdae);
        }

    }

    @Override
    public TestCase save(TestCase entity, UUID userId) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(entity.getTestCaseRequest());
        int rowCount = jdbcTemplate.update(UPSERT_TEST_CASE, entity.getId(), entity.getName(), json, userId);

        if (rowCount == 1) {
            return entity;
        } else {
            return jdbcTemplate.queryForObject("SELECT * FROM test_app.test_case WHERE id = ?", testCaseRowMapper, entity.getId());
        }
    }

    @Override
    public UUID delete(UUID id) throws EntityNotFoundException {
        try {
            return jdbcTemplate.queryForObject(DELETE_BY_ID, UUID.class, id);
        } catch (EmptyResultDataAccessException erdae) {
            throw new EntityNotFoundException("Test case with ID " + id + " not found", erdae);
        }
    }
}
