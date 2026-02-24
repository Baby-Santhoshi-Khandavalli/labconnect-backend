package com.labconnect.mapper;

import com.labconnect.DTORequest.TestWorkflowRequestDTO;
import com.labconnect.DTOResponse.TestWorkflowResponseDTO;
import com.labconnect.models.TestWorkflow;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TestWorkflowMapper {

    // Entity → Response DTO
    @Mapping(source = "workflowID", target = "workflowId")
    @Mapping(source = "labOrder.orderId", target = "orderId")
    @Mapping(source = "test.testId", target = "testId")
    TestWorkflowResponseDTO toResponseDTO(TestWorkflow workflow);

    // Request DTO → Entity
    @Mapping(source = "orderId", target = "labOrder.orderId")
    @Mapping(source = "testId", target = "test.testId")
    TestWorkflow toEntity(TestWorkflowRequestDTO dto);
}
