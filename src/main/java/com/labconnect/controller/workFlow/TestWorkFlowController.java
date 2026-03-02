package com.labconnect.controller.workFlow;

import com.labconnect.DTORequest.workFlow.TestWorkFlowRequestDTO;
import com.labconnect.DTOResponse.workFlow.TestWorkFlowResponseDTO;
import com.labconnect.mapper.workflow.TestWorkFlowMapper;
import com.labconnect.models.workFlow.TestWorkFlow;
import com.labconnect.services.workFlow.TestWorkFlowService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workflows")
public class TestWorkFlowController {
    private final TestWorkFlowService service;
    private final TestWorkFlowMapper mapper;

    public TestWorkFlowController(TestWorkFlowService service, TestWorkFlowMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<TestWorkFlowResponseDTO> getAllWorkflows() {
        return service.getAllWorkflows()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @PostMapping
    public TestWorkFlowResponseDTO createWorkflow(@Valid @RequestBody TestWorkFlowRequestDTO dto) {
        TestWorkFlow workflow = mapper.toEntity(dto);
        return mapper.toResponseDTO(service.createWorkflow(workflow));
    }

    @PutMapping("/{id}/status")
    public TestWorkFlowResponseDTO updateWorkflowStatus(@PathVariable Long id, @RequestParam String status) {
        return mapper.toResponseDTO(service.updateWorkflowStatus(id, status));
    }
}