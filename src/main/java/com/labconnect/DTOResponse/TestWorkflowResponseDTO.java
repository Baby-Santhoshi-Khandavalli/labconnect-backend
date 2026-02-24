package com.labconnect.DTOResponse;

import com.labconnect.Enum.WorkflowStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TestWorkflowResponseDTO {
    private Long workflowId;
    private Long orderId;
    private Long testId;
    private WorkflowStatus status;
    private String assignedTo;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
