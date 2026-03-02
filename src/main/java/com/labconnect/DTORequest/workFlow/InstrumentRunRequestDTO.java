package com.labconnect.DTORequest.workFlow;

import com.labconnect.Enum.RunStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class InstrumentRunRequestDTO {

    @NotNull(message = "Test ID is required")
    private Long testId;

    @NotNull(message = "Instrument name is required")
    @Size(min = 2, max = 50, message = "Instrument name must be between 2 and 50 characters")
    private String instrumentName;

    @NotNull(message = "Run date is required")
    private Date runDate;

    @NotNull(message = "Status is required")
    private RunStatus status;
}
