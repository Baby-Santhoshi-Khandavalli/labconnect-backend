package com.labconnect.DTORequest.qualityControl;

import lombok.Data;

//package com.labconnect.dto;
@Data
public class ComplianceRecordRequestDTO {
    private Long testId;
    private String auditType;
    private String notes;
}
