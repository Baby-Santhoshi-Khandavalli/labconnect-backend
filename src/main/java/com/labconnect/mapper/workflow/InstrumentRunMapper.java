package com.labconnect.mapper.workflow;



import com.labconnect.DTORequest.workFlow.InstrumentRunRequestDTO;
import com.labconnect.DTOResponse.workFlow.InstrumentRunResponseDTO;
import com.labconnect.models.workFlow.InstrumentRun;
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
