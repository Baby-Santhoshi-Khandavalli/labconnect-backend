package com.labconnect.controller.qualityControl;

import com.labconnect.DTORequest.QualityControlRequestDTO;
import com.labconnect.DTOResponse.QualityControlResponseDTO;
import com.labconnect.mapper.QualityControlMapper;
import com.labconnect.models.QualityControl;
import com.labconnect.services.QualityControlService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/qc")
public class QualityControlController {
    private final QualityControlService service;
    private final QualityControlMapper mapper;

    public QualityControlController(QualityControlService service, QualityControlMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<QualityControlResponseDTO> getAllQCLogs() {
        return service.getAllQCLogs()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @PostMapping
    public QualityControlResponseDTO createQCLog(@Valid @RequestBody QualityControlRequestDTO dto) {
        QualityControl qc = mapper.toEntity(dto);
        return mapper.toResponseDTO(service.createQCLog(qc));
    }

    @PutMapping("/{id}/status")
    public QualityControlResponseDTO updateQCStatus(@PathVariable Long id, @RequestParam String status) {
        return mapper.toResponseDTO(service.updateQCStatus(id, status));
    }
}
