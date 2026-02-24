package com.labconnect.DTOResponse;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResultAuthorizationResponseDTO {
    private Long authorizationId;
    private Long orderId;
    private Long pathologistId;
    private LocalDateTime authorizedDate;
    private String remarks;
}
