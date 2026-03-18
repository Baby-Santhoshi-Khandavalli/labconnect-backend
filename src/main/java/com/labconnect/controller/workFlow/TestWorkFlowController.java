package com.labconnect.controller.workFlow;

import com.labconnect.DTORequest.workFlow.TestWorkFlowRequestDTO;
import com.labconnect.DTOResponse.workFlow.TestWorkFlowResponseDTO;
import com.labconnect.mapper.workflow.TestWorkFlowMapper;
import com.labconnect.models.workFlow.TestWorkFlow;
import com.labconnect.services.workFlow.TestWorkFlowService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test-workflows")
public class TestWorkFlowController {

    private final TestWorkFlowService service;
    private final TestWorkFlowMapper mapper;

    public TestWorkFlowController(TestWorkFlowService service, TestWorkFlowMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    // GET /api/test-workflows
    @GetMapping
    public List<TestWorkFlowResponseDTO> getAllWorkflows() {
        return service.getAllWorkflows()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    // POST /api/test-workflows
    @PostMapping
    public ResponseEntity<TestWorkFlowResponseDTO> createWorkflow(@Valid @RequestBody TestWorkFlowRequestDTO dto) {
        TestWorkFlow workflow = mapper.toEntity(dto);
        TestWorkFlow saved = service.createWorkflow(workflow);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(saved));
    }

    // PUT /api/test-workflows/{id}/status?status=COMPLETED
    @PutMapping("/{id}/status")
    public ResponseEntity<TestWorkFlowResponseDTO> updateWorkflowStatus(@PathVariable Long id,
                                                                        @RequestParam String status) {
        TestWorkFlow updated = service.updateWorkflowStatus(id, status);
        return ResponseEntity.ok(mapper.toResponseDTO(updated));
    }
}
