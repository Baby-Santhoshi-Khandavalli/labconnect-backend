package com.labconnect.controller.workFlow;

import com.labconnect.DTORequest.workFlow.InstrumentRunRequestDTO;
import com.labconnect.DTOResponse.workFlow.InstrumentRunResponseDTO;
import com.labconnect.mapper.workflow.InstrumentRunMapper;

import com.labconnect.models.workFlow.InstrumentRun;
import com.labconnect.services.workFlow.InstrumentRunService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<InstrumentRunResponseDTO>> getAllRuns() {
        List<InstrumentRunResponseDTO> runs = service.getAllRuns()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(runs);
    }


    @GetMapping("/by-test/{testId}")
    public ResponseEntity<List<InstrumentRunResponseDTO>> getRunsByTest(@PathVariable Long testId) {
        List<InstrumentRunResponseDTO> runs = service.getRunsByTest(testId)
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(runs);
    }

    // Get runs by instrument name
    @GetMapping("/by-instrument/{instrumentName}")
    public ResponseEntity<List<InstrumentRunResponseDTO>> getRunsByInstrument(@PathVariable String instrumentName) {
        List<InstrumentRunResponseDTO> runs = service.getRunsByInstrument(instrumentName)
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(runs);
    }

    // Create a new run
    @PostMapping
    public ResponseEntity<InstrumentRunResponseDTO> createRun(@Valid @RequestBody InstrumentRunRequestDTO dto) {
        InstrumentRun run = mapper.toEntity(dto);
        InstrumentRun saved = service.createRun(run);
        return ResponseEntity.ok(mapper.toResponseDTO(saved));
    }

    // Update run status
    @PutMapping("/{id}/status")
    public ResponseEntity<InstrumentRunResponseDTO> updateRunStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        InstrumentRun updated = service.updateRunStatus(id, status);
        return ResponseEntity.ok(mapper.toResponseDTO(updated));
    }
}
