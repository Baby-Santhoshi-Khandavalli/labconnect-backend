package com.labconnect.DTOResponse;

import com.labconnect.Enum.RunStatus;
import lombok.Data;

import java.util.Date;

@Data
public class InstrumentRunResponseDTO {
    private Long runId;
    private Long testId;
    private String instrumentName;
    private Date runDate;
    private RunStatus status;
}
