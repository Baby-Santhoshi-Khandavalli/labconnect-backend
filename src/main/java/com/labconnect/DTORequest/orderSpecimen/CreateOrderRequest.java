package com.labconnect.DTORequest.orderSpecimen;

import com.labconnect.models.orderSpecimen.LabOrder;
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
