package com.labconnect.controller.qualityControl;

import com.labconnect.DTORequest.ComplianceRecordRequestDTO;
import com.labconnect.DTOResponse.ComplianceRecordResponseDTO;
import com.labconnect.services.ComplianceRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance")
public class ComplianceRecordController {

    private final ComplianceRecordService service;

    public ComplianceRecordController(ComplianceRecordService service) {
        this.service = service;
    }

    @PostMapping
    public ComplianceRecordResponseDTO createRecord(@RequestBody ComplianceRecordRequestDTO request) {
        return service.createComplianceRecord(request);
    }

    @GetMapping("/test/{testId}")
    public List<ComplianceRecordResponseDTO> getRecordsByTest(@PathVariable Long testId) {
        return service.getRecordsByTestId(testId);
    }

    @GetMapping("/audit/{auditType}")
    public List<ComplianceRecordResponseDTO> getRecordsByAuditType(@PathVariable String auditType) {
        return service.getRecordsByAuditType(auditType);
    }

    @GetMapping
    public List<ComplianceRecordResponseDTO> getAllRecords() {
        return service.getAllRecords();
    }
}
