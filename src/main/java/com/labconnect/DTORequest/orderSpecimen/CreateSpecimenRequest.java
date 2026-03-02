package com.labconnect.DTORequest.orderSpecimen;


import com.labconnect.models.orderSpecimen.Specimen;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateSpecimenRequest {
    private Specimen.SpecimenType specimenType;
    private LocalDateTime collectedDate;
    private Long collectorId;
}
