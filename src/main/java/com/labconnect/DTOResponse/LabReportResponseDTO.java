
package com.labconnect.DTOResponse;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LabReportResponseDTO {
    private Long reportId;
    private String scope;
    private String metrics;
    private LocalDateTime generatedDate;
}