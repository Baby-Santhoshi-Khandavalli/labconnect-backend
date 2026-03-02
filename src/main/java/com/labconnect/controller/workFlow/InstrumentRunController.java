package com.labconnect.controller.workFlow;

import com.labconnect.DTORequest.workFlow.InstrumentRunRequestDTO;
import com.labconnect.DTOResponse.workFlow.InstrumentRunResponseDTO;
import com.labconnect.mapper.workflow.InstrumentRunMapper;

import com.labconnect.models.workFlow.InstrumentRun;
import com.labconnect.services.workFlow.InstrumentRunService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instrument-runs")
public class InstrumentRunController {
    private final InstrumentRunService service;
    private final InstrumentRunMapper mapper;

    public InstrumentRunController(InstrumentRunService service, InstrumentRunMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<InstrumentRunResponseDTO> getAllRuns() {
        return service.getAllRuns()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @PostMapping
    public InstrumentRunResponseDTO createRun(@Valid @RequestBody InstrumentRunRequestDTO dto) {
        InstrumentRun run = mapper.toEntity(dto);
        return mapper.toResponseDTO(service.createRun(run));
    }

    @PutMapping("/{id}/status")
    public InstrumentRunResponseDTO updateRunStatus(@PathVariable Long id, @RequestParam String status) {
        return mapper.toResponseDTO(service.updateRunStatus(id, status));
    }
}
