package ru.develonica.load.testing.web.test.model.mapper;

import org.mapstruct.Mapper;
import ru.develonica.load.testing.web.test.model.dto.TestCaseDto;
import ru.develonica.load.testing.web.test.model.object.TestCase;

import java.util.List;

/**
 *  Маппер TestCase <-> TestCaseDto
 */
@Mapper(componentModel = "spring")
public interface TestCaseMapper {
    TestCase testCaseDtoToEntity(TestCaseDto testCaseDto);
    TestCaseDto testCaseToDto(TestCase testCase);

    List<TestCaseDto> testCaseListToDtoList(List<TestCase> testCaseList);
}
