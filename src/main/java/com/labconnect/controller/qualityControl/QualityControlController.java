package com.labconnect.controller.qualityControl;


import com.labconnect.DTORequest.qualityControl.QualityControlRequestDTO;
import com.labconnect.DTOResponse.qualityControl.QualityControlResponseDTO;
import com.labconnect.Enum.QCStatus;

import com.labconnect.mapper.qualityControl.QualityControlMapper;
import com.labconnect.models.qualityControl.QualityControl;
import com.labconnect.services.qualityControl.QualityControlService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quality-controls")
public class QualityControlController {
    private final QualityControlService service;
    private final QualityControlMapper mapper;

    public QualityControlController(QualityControlService service, QualityControlMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    // GET /api/quality-controls
    @GetMapping
    public List<QualityControlResponseDTO> getAllQCLogs() {
        return service.getAllQCLogs()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    // POST /api/quality-controls
    @PostMapping
    public QualityControlResponseDTO createQCLog(@Valid @RequestBody QualityControlRequestDTO dto) {
        QualityControl qc = mapper.toEntity(dto);
        return mapper.toResponseDTO(service.createQCLog(qc));
    }

    // PATCH /api/quality-controls/{id}/status
    @PatchMapping("/{id}/status")
    public ResponseEntity<QualityControlResponseDTO> updateQCStatus(@PathVariable Long id,
                                                                    @RequestParam QCStatus status) {
        // Pass the enum directly to the service
        QualityControlResponseDTO response = mapper.toResponseDTO(service.updateQCStatus(id, String.valueOf(status)));
        return ResponseEntity.ok(response);
    }
}
