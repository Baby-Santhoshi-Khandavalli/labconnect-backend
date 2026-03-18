package com.labconnect.DTOResponse.Identity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuditLogDTO {
    private Long auditId;
    private String action;
    private String resource;
    private LocalDateTime timestamp;
    private String metadata;
}
