package com.labconnect.mapper.labReport;

import com.labconnect.DTORequest.LabReportRequestDTO;
import com.labconnect.DTOResponse.LabReportResponseDTO;
import com.labconnect.models.LabReport;
import org.mapstruct.*;

        import java.util.List;

@Mapper(componentModel = "spring")
public interface LabReportMapper {

    // LabReportRequestDTO -> LabReport
    @Mappings({
            @Mapping(target = "reportId", ignore = true), // DB-generated ID
            @Mapping(source = "scope",         target = "scope"),
            @Mapping(source = "metrics",       target = "metrics"),
            @Mapping(source = "generatedDate", target = "generatedDate")
    })
    LabReport toEntity(LabReportRequestDTO dto);

    // LabReport -> LabReportResponseDTO
    @Mappings({
            @Mapping(source = "reportId",     target = "reportId"),
            @Mapping(source = "scope",        target = "scope"),
            @Mapping(source = "metrics",      target = "metrics"),
            @Mapping(source = "generatedDate",target = "generatedDate")
    })
    LabReportResponseDTO toResponseDTO(LabReport entity);

    // List mapping helper
    List<LabReportResponseDTO> toResponseDTOList(List<LabReport> entities);

    // Partial update: ignore null properties
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(source = "scope",         target = "scope"),
            @Mapping(source = "metrics",       target = "metrics"),
            @Mapping(source = "generatedDate", target = "generatedDate")
    })
    void updateEntityFromDto(LabReportRequestDTO dto, @MappingTarget LabReport entity);
}
