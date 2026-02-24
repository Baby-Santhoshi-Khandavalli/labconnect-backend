package com.labconnect.mapper;

import com.labconnect.dto.request.QualityControlRequestDTO;
import com.labconnect.dto.response.QualityControlResponseDTO;
import com.labconnect.models.QualityControl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QualityControlMapper {

    // Entity → Response DTO
    @Mapping(source = "qcId", target = "qcId")
    @Mapping(source = "test.testId", target = "testId")
    QualityControlResponseDTO toResponseDTO(QualityControl qc);

    // Request DTO → Entity
    @Mapping(source = "testId", target = "test.testId")
    QualityControl toEntity(QualityControlRequestDTO dto);
}
