package com.labconnect.DTORequest.labReport;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LabReportRequestDTO {

    @NotBlank(message = "Scope is required")
    private String scope;          // Department/Test/Period

    @NotBlank(message = "Metrics are required")
    private String metrics;        // JSON or string representation of metrics

    @NotNull(message = "Generated date is required")
    private LocalDateTime generatedDate;
}
