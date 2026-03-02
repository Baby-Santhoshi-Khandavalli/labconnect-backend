package com.labconnect.mapper.qualityControl;

import com.labconnect.DTORequest.ComplianceRecordRequestDTO;
import com.labconnect.DTOResponse.ComplianceRecordResponseDTO;
import com.labconnect.models.ComplianceRecord;
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
