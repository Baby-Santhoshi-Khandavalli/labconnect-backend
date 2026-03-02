package com.labconnect.mapper.workflow;
import com.labconnect.DTORequest.workFlow.TestWorkFlowRequestDTO;
import com.labconnect.DTOResponse.workFlow.TestWorkFlowResponseDTO;
import com.labconnect.models.orderSpecimen.LabOrder;
import com.labconnect.models.testCatalog.Test;
import com.labconnect.models.workFlow.TestWorkFlow;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TestWorkFlowMapper {

    // Entity → Response DTO
    @Mapping(source = "workflowID", target = "workflowId")
    @Mapping(source = "order.orderId", target = "orderId")
    @Mapping(source = "test.testId", target = "testId")
    TestWorkFlowResponseDTO toResponseDTO(TestWorkFlow workflow);

    // Request DTO → Entity
    @Mapping(source = "orderId", target = "order")
    @Mapping(source = "testId", target = "test")
    TestWorkFlow toEntity(TestWorkFlowRequestDTO dto);

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
