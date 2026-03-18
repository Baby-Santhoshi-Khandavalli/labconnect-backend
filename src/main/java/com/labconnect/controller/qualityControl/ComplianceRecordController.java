package com.labconnect.controller.qualityControl;


import com.labconnect.DTORequest.qualityControl.ComplianceRecordRequestDTO;
import com.labconnect.DTOResponse.qualityControl.ComplianceRecordResponseDTO;
import com.labconnect.services.qualityControl.ComplianceRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance-records")
public class ComplianceRecordController {

    private final ComplianceRecordService service;

    public ComplianceRecordController(ComplianceRecordService service) {
        this.service = service;
    }

    // Create a record
    @PostMapping
    public ResponseEntity<ComplianceRecordResponseDTO> createRecord(@RequestBody ComplianceRecordRequestDTO request) {
        ComplianceRecordResponseDTO response = service.createComplianceRecord(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Get all records, optionally filtered
    @GetMapping
    public ResponseEntity<List<ComplianceRecordResponseDTO>> getRecords(
            @RequestParam(required = false) Long testId,
            @RequestParam(required = false) String auditType) {

        if (testId != null) {
            return ResponseEntity.ok(service.getRecordsByTestId(testId));
        } else if (auditType != null) {
            return ResponseEntity.ok(service.getRecordsByAuditType(auditType));
        } else {
            return ResponseEntity.ok(service.getAllRecords());
        }
    }
}
