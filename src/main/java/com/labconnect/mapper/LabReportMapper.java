package com.labconnect.mapper;

import com.labconnect.DTORequest.LabReportRequestDTO;
import com.labconnect.DTOResponse.LabReportResponseDTO;
import com.labconnect.models.LabReport;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LabReportMapper {

    // LabReportRequestDTO -> LabReport (explicit field mapping; ignore DB-generated ID)
    @Mappings({
            @Mapping(target = "reportId", ignore = true),
            @Mapping(source = "dto.scope",         target = "scope"),
            @Mapping(source = "dto.metrics",       target = "metrics"),
            @Mapping(source = "dto.generatedDate", target = "generatedDate")
    })
    LabReport toEntity(LabReportRequestDTO dto);

    // LabReport -> LabReportResponseDTO (explicit mappings for clarity)
    @Mappings({
            @Mapping(source = "entity.reportId",     target = "reportId"),
            @Mapping(source = "entity.scope",        target = "scope"),
            @Mapping(source = "entity.metrics",      target = "metrics"),
            @Mapping(source = "entity.generatedDate",target = "generatedDate")
    })
    LabReportResponseDTO toResponseDTO(LabReport entity);

    // List mapping helper
    List<LabReportResponseDTO> toResponseDTOList(List<LabReport> entities);

    // Partial update: ignore null properties from DTO
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(source = "dto.scope",         target = "scope"),
            @Mapping(source = "dto.metrics",       target = "metrics"),
            @Mapping(source = "dto.generatedDate", target = "generatedDate")
    })
    void updateEntityFromDto(LabReportRequestDTO dto, @MappingTarget LabReport entity);
}