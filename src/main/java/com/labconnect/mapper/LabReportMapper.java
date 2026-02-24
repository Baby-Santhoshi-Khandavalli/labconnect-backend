package com.labconnect.mapper;

import com.labconnect.DTORequest.LabReportRequestDTO;
import com.labconnect.DTOResponse.LabReportResponseDTO;
import com.labconnect.models.LabReport;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LabReportMapper {

    LabReport toEntity(LabReportRequestDTO dto);

    LabReportResponseDTO toResponseDTO(LabReport entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(LabReportRequestDTO dto, @MappingTarget LabReport entity);
}