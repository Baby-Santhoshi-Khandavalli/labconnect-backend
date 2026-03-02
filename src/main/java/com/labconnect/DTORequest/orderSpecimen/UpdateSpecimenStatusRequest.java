package com.labconnect.DTORequest.orderSpecimen;


import com.labconnect.models.orderSpecimen.Specimen;
import lombok.Data;

@Data
public class UpdateSpecimenStatusRequest {
    private Specimen.SpecimenStatus status;
}
