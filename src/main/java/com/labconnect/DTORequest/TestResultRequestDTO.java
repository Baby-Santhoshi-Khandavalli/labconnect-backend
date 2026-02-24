
package com.labconnect.DTORequest;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TestResultRequestDTO {
    private Long workflowId;
    private Long parameterId;
    private String value;
    private String flag;       // Normal / High / Low / Critical
    private String enteredBy;
    private LocalDateTime enteredDate;
}
