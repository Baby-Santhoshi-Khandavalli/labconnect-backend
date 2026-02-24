package com.labconnect.DTOResponse;

import com.labconnect.Enum.QCStatus;
import lombok.Data;

import java.util.Date;

@Data
public class QualityControlResponseDTO {
    private Long qcId;
    private Long testId;
    private Date runDate;
    private Double qcValue;
    private QCStatus status;
}
