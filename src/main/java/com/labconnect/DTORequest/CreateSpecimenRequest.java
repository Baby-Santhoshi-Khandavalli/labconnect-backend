package com.labconnect.DTORequest;

import com.labconnect.models.Specimen;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateSpecimenRequest {
    private Specimen.SpecimenType specimenType;
    private LocalDateTime collectedDate;
    private Long collectorId;
}
