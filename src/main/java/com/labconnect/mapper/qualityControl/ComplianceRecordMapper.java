package com.labconnect.mapper.qualityControl;

import com.labconnect.DTORequest.qualityControl.ComplianceRecordRequestDTO;
import com.labconnect.DTOResponse.qualityControl.ComplianceRecordResponseDTO;
import com.labconnect.models.qualityControl.ComplianceRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ComplianceRecordMapper {

    // Request DTO → Entity
    @Mapping(target = "recordId", ignore = true) // ID is auto-generated
    @Mapping(target = "loggedDate", ignore = true) // set in service
    ComplianceRecord toEntity(ComplianceRecordRequestDTO dto);

    // Entity → Response DTO
    ComplianceRecordResponseDTO toResponseDto(ComplianceRecord entity);
}
