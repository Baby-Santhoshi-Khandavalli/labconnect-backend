package com.labconnect.mapper;

import com.labconnect.DTORequest.InstrumentRunRequestDTO;
import com.labconnect.DTOResponse.InstrumentRunResponseDTO;
import com.labconnect.models.InstrumentRun;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InstrumentRunMapper {

    // Entity → Response DTO
    @Mapping(source = "runId", target = "runId")
    @Mapping(source = "test.testId", target = "testId")
    InstrumentRunResponseDTO toResponseDTO(InstrumentRun run);

    // Request DTO → Entity
    @Mapping(source = "testId", target = "test.testId")
    InstrumentRun toEntity(InstrumentRunRequestDTO dto);
}
