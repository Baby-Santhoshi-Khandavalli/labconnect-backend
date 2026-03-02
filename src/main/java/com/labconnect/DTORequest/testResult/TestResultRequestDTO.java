package com.labconnect.DTORequest.testResult;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TestResultRequestDTO {
    private Long workflowId;
    private Long parameterId;
    private String value;
    private String flag;       // Normal / High / Low / Critical
    //    private String enteredBy;
    private Long enteredBy;
    private LocalDateTime enteredDate;
}
