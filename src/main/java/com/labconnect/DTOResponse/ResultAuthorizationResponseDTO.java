package com.labconnect.DTOResponse;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ResultAuthorizationResponseDTO {
    private Long authorizationId;
    private Long orderId;
    private Long resultId; // ADD THIS: MapStruct needs this target
    private Long pathologistId;
    private LocalDateTime authorizedDate;
    private String remarks;
}