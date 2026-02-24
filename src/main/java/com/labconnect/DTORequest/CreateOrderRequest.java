package com.labconnect.DTORequest;

import com.labconnect.models.LabOrder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class CreateOrderRequest {
    private Long patientId;
    private Long clinicianId;
    private LocalDateTime orderDate;
    private LabOrder.Priority priority;
    private Set<Long> testIds;
}