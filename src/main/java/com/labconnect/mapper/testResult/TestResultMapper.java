package com.labconnect.mapper.testResult;

import com.labconnect.DTORequest.TestResultRequestDTO;
import com.labconnect.DTOResponse.TestResultResponseDTO;
import com.labconnect.Enum.Flag;
import com.labconnect.models.TestResult;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TestResultMapper {

    @Mappings({
            @Mapping(source = "resultId", target = "resultId"),
            @Mapping(source = "workflow.workflowID", target = "workflowId"),
            @Mapping(source = "parameter.parameterId", target = "parameterId"),
            @Mapping(source = "value", target = "value"),
            @Mapping(source = "flag", target = "flag", qualifiedByName = "flagToString"),
            @Mapping(source = "enteredBy.userId", target = "enteredBy"),
            @Mapping(source = "enteredDate", target = "enteredDate")
    })
    TestResultResponseDTO toResponseDTO(TestResult entity);

    @Mappings({
            @Mapping(target = "resultId", ignore = true),
            @Mapping(target = "workflow", ignore = true),
            @Mapping(target = "parameter", ignore = true),
            @Mapping(source = "value", target = "value"),
            @Mapping(source = "flag", target = "flag", qualifiedByName = "stringToFlag"),
            //@Mapping(source = "enteredBy", target = "enteredBy"),
            @Mapping(target = "enteredBy",ignore = true),

            @Mapping(source = "enteredDate", target = "enteredDate"),
            @Mapping(target = "authorization", ignore = true)
    })
    TestResult toEntitySansRelations(TestResultRequestDTO dto);

    @Named("flagToString")
    default String flagToString(Flag flag) {
        return flag != null ? flag.name() : null;
    }

    @Named("stringToFlag")
    default Flag stringToFlag(String s) {
        return (s != null && !s.isBlank()) ? Flag.valueOf(s) : null;
    }
}