
package com.labconnect.mapper;
import org.mapstruct.Mapping;
import com.labconnect.DTORequest.TestRequest;
import com.labconnect.DTORespone.TestResponse;
import com.labconnect.Enum.Department;
import com.labconnect.Enum.Method;
import com.labconnect.Enum.Status;
import com.labconnect.models.Test;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface TestMapper {

    @Mappings({
            @Mapping(target = "testId", ignore = true),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "department", expression = "java(toDepartment(req.getDepartment()))"),
            @Mapping(target = "method",     expression = "java(toMethod(req.getMethod()))"),
            @Mapping(target = "status",     expression = "java(toStatus(req.getStatus()))"),
            @Mapping(target = "parameters", ignore = true),
            @Mapping(target = "panelMappings", ignore = true)
    })
    Test toEntity(TestRequest req);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target = "name",       source = "req.name"),
            @Mapping(target = "department", expression = "java(toDepartment(req.getDepartment()))"),
            @Mapping(target = "method",     expression = "java(toMethod(req.getMethod()))"),
            @Mapping(target = "status",     expression = "java(toStatus(req.getStatus()))")
    })
    void updateEntity(TestRequest req, @MappingTarget Test entity);
    @Mappings({
            @Mapping(target = "testId",     source = "testId"),
            @Mapping(target = "name",       source = "name"),
            @Mapping(target = "department", expression = "java(fromDepartment(entity.getDepartment()))"),
            @Mapping(target = "method",     expression = "java(fromMethod(entity.getMethod()))"),
            @Mapping(target = "status",     expression = "java(fromStatus(entity.getStatus()))")
    })
    TestResponse toResponseDto(Test entity);

    default Department toDepartment(String value) {
        if (value == null || value.isBlank()) return null;
        return Department.valueOf(value.trim().toUpperCase());
    }
    default Method toMethod(String value) {
        if (value == null || value.isBlank()) return null;
        return Method.valueOf(value.trim().toUpperCase());
    }
    default Status toStatus(String value) {
        if (value == null || value.isBlank()) return null;
        return Status.valueOf(value.trim().toUpperCase());
    }

    default String fromDepartment(Department e) { return e == null ? null : e.name(); }
    default String fromMethod(Method e)         { return e == null ? null : e.name(); }
    default String fromStatus(Status e)         { return e == null ? null : e.name(); }
}
