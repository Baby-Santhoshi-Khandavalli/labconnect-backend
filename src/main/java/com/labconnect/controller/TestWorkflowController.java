package com.labconnect.controller;

import com.labconnect.DTORequest.TestWorkflowRequestDTO;
import com.labconnect.DTOResponse.TestWorkflowResponseDTO;
import com.labconnect.mapper.TestWorkflowMapper;
import com.labconnect.models.TestWorkflow;
import com.labconnect.services.TestWorkflowService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workflows")
public class TestWorkflowController {
    private final TestWorkflowService service;
    private final TestWorkflowMapper mapper;

    public TestWorkflowController(TestWorkflowService service, TestWorkflowMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<TestWorkflowResponseDTO> getAllWorkflows() {
        return service.getAllWorkflows()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @PostMapping
    public TestWorkflowResponseDTO createWorkflow(@Valid @RequestBody TestWorkflowRequestDTO dto) {
        TestWorkflow workflow = mapper.toEntity(dto);
        return mapper.toResponseDTO(service.createWorkflow(workflow));
    }

    @PutMapping("/{id}/status")
    public TestWorkflowResponseDTO updateWorkflowStatus(@PathVariable Long id, @RequestParam String status) {
        return mapper.toResponseDTO(service.updateWorkflowStatus(id, status));
    }
//    @PostMapping("/panels/{panelId}/orders/{orderId}/spawn")
//    public List<TestWorkflowResponseDTO> spawnForPanel(
//            @PathVariable Long panelId,
//            @PathVariable Long orderId,
//            @RequestParam(required = false) String assignedTo) {
//        return service.spawnWorkflowsForPanel(panelId, orderId, assignedTo)
//                .stream()
//                .map(mapper::toResponseDTO)
//                .toList();
//    }
}
