//package com.labconnect.DTORequest;
//
//import com.labconnect.Enum.WorkflowStatus;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Size;
//
//import lombok.Data;
//import java.time.LocalDateTime;
//
//@Data
//public class TestWorkflowRequestDTO {
//
//    @NotNull(message = "Order ID is required")
//    private Long orderId;
//
//    @NotNull(message = "Test ID is required")
//    private Long testId;
//
//    @NotNull(message = "Workflow status is required")
//    private WorkflowStatus status;
//
//    @Size(max = 100, message = "AssignedTo must not exceed 100 characters")
//    private String assignedTo;
//
//    private LocalDateTime startTime;
//    private LocalDateTime endTime;
//}
