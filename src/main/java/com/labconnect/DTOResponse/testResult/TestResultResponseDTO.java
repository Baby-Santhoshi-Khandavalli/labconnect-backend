package com.labconnect.DTOResponse.testResult;

import com.labconnect.models.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TestResultResponseDTO {
    private Long resultId;
    private Long workflowId;
    private Long parameterId;
    private String value;
    private String flag;
    //from 1st string then user now Long
    private Long enteredBy;
    private LocalDateTime enteredDate;
}
