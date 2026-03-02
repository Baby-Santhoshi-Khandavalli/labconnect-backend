package com.labconnect.DTORequest.qualityControl;

import com.labconnect.Enum.QCStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class QualityControlRequestDTO {

    @NotNull(message = "Test ID is required")
    private Long testId;

    @NotNull(message = "Run date is required")
    private Date runDate;

    @NotNull(message = "QC value is required")
    @DecimalMin(value = "0.0", message = "QC value must be positive")
    private Double qcValue;

    @NotNull(message = "Status is required")
    private QCStatus status;
}
