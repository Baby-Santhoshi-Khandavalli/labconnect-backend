package com.labconnect.controller;

import com.labconnect.DTORequest.InstrumentRunRequestDTO;
import com.labconnect.DTOResponse.InstrumentRunResponseDTO;
import com.labconnect.mapper.InstrumentRunMapper;
import com.labconnect.models.InstrumentRun;
import com.labconnect.services.InstrumentRunService;
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
