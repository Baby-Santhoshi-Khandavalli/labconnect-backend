package com.labconnect.DTORequest;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResultAuthorizationRequestDTO {
    private Long orderId;
    private Long resultId;
    private Long pathologistId;
    private LocalDateTime authorizedDate;
    private String remarks;
}
