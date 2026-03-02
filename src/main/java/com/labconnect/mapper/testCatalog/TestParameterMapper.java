package com.labconnect.mapper.testCatalog;
import com.labconnect.DTORequest.testCatalog.TestParameterRequest;
import com.labconnect.DTOResponse.testCatalog.TestParameterResponse;
import com.labconnect.Enum.Flag;
import com.labconnect.models.Test;
import com.labconnect.models.TestParameter;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TestParameterMapper {
    @Mappings({
            @Mapping(target = "parameterId", ignore = true),
            @Mapping(target = "name", source = "req.name"),
            @Mapping(target = "unit", source = "req.unit"),
            @Mapping(target = "referenceRange", source = "req.referenceRange"),
            @Mapping(target = "criticalRange", expression = "java(toFlag(req.getCriticalRange()))"),
            @Mapping(target = "test", source = "test")
    })
    TestParameter toEntity(TestParameterRequest req, Test test);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target = "name", source = "req.name"),
            @Mapping(target = "unit", source = "req.unit"),
            @Mapping(target = "referenceRange", source = "req.referenceRange"),
            @Mapping(target = "criticalRange", expression = "java(toFlag(req.getCriticalRange()))")
            // NOTE: Relinking to another Test is handled in Service by setting entity.setTest(newTest) explicitly.
    })
    void updateEntity(TestParameterRequest req, @MappingTarget TestParameter entity);

    @Mappings({
            @Mapping(target = "parameterId", source = "parameterId"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "unit", source = "unit"),
            @Mapping(target = "referenceRange", source = "referenceRange"),
            @Mapping(target = "criticalRange", expression = "java(fromFlag(entity.getCriticalRange()))"),
            @Mapping(target = "testId", expression = "java(entity.getTest() == null ? null : entity.getTest().getTestId())")
    })
    TestParameterResponse toResponse(TestParameter entity);
    default Flag toFlag(String value) {
        if (value == null || value.isBlank()) return null;
        return Flag.valueOf(value.trim().toUpperCase());
    }
    default String fromFlag(Flag e) { return e == null ? null : e.name(); }
}

