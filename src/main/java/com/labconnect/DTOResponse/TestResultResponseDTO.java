package com.labconnect.DTOResponse;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TestResultResponseDTO {
    private Long resultId;
    private Long workflowId;
    private Long parameterId;
    private String value;
    private String flag;
    private String enteredBy;
    private LocalDateTime enteredDate;
}
