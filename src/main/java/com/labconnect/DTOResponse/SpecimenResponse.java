package com.labconnect.DTOResponse;

import com.labconnect.models.Specimen;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SpecimenResponse {
    private Long specimenId;
    private Long orderId;
    private Specimen.SpecimenType specimenType;
    private LocalDateTime collectedDate;
    private Long collectorId;
    private Specimen.SpecimenStatus status;
    private String barcodeValue;
    private String labelText;
}