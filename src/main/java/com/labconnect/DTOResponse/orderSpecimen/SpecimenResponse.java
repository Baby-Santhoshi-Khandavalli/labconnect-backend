package com.labconnect.DTOResponse.orderSpecimen;


import com.labconnect.models.orderSpecimen.Specimen;
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