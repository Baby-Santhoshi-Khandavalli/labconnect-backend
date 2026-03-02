package com.labconnect.mapper.workflow;


//import com.labconnect.dto.request.TestWorkflowRequestDTO;
import com.labconnect.DTORequest.TestWorkflowRequestDTO;
import com.labconnect.DTOResponse.TestWorkflowResponseDTO;
import com.labconnect.models.LabOrder;
import com.labconnect.models.Test;
import com.labconnect.models.TestWorkflow;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TestWorkFlowMapper {

    // Entity → Response DTO
    @Mapping(source = "workflowID", target = "workflowId")
    @Mapping(source = "order.orderId", target = "orderId")
    @Mapping(source = "test.testId", target = "testId")
    TestWorkflowResponseDTO toResponseDTO(TestWorkflow workflow);

    // Request DTO → Entity
    @Mapping(source = "orderId", target = "order")
    @Mapping(source = "testId", target = "test")
    TestWorkflow toEntity(TestWorkflowRequestDTO dto);

    // Helper methods for nested mapping
    default LabOrder mapOrderId(Long orderId) {
        if (orderId == null) return null;
        LabOrder order = new LabOrder();
        order.setOrderId(orderId);
        return order;
    }

    default Test mapTestId(Long testId) {
        if (testId == null) return null;
        Test test = new Test();
        test.setTestId(testId);
        return test;
    }
}
