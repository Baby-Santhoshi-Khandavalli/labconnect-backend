package com.labconnect.services;

import com.labconnect.Exception.ResourceNotFoundException;
import com.labconnect.models.TestWorkflow;
import com.labconnect.repository.TestWorkflowRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestWorkflowService {
    private final TestWorkflowRepository repository;
//    private final PanelMappingRepository panelMappingRepo;
//    private final TestRepository testRepo;

//    public TestWorkflowService(TestWorkflowRepository repository, PanelMappingRepository panelMappingRepo, TestRepository testRepo) {
//        this.repository = repository;
//        this.panelMappingRepo = panelMappingRepo;
//        this.testRepo = testRepo;
//    }
        public TestWorkflowService(TestWorkflowRepository repository) {
        this.repository = repository;
    }

    public List<TestWorkflow> getAllWorkflows() {
        return repository.findAll();
    }

    public TestWorkflow createWorkflow(TestWorkflow workflow) {
        return repository.save(workflow);
    }

    public TestWorkflow updateWorkflowStatus(Long id, String status) {
        TestWorkflow workflow = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workflow not found with id: " + id));
        workflow.setStatus(Enum.valueOf(com.labconnect.Enum.WorkflowStatus.class, status));
        return repository.save(workflow);
    }
//    @Transactional
//    public List<TestWorkflow> spawnWorkflowsForPanel(Long panelId, Long orderId, String assignedTo) {
//        // 1) fetch panel → tests
//        var mappings = panelMappingRepo.findByPanel_PanelId(panelId);
//
//        if (mappings.isEmpty()) {
//            throw new com.labconnect.exceptions.ResourceNotFoundException("No tests mapped to panel: " + panelId);
//        }
//
//        // 2) for each test, if not exists (orderId+testId), create workflow
//        var toSave = new ArrayList<TestWorkflow>();
//        for (var m : mappings) {
//            var test = m.getTest();
//            if (test == null) continue;
//
//            boolean exists = workflowRepo.existsByLabOrder_OrderIdAndTest_TestId(orderId, test.getTestId());
//            if (exists) continue;
//
//            var wf = new TestWorkflow();
//
//            // link LabOrder by id only (no fetch needed)
//            var orderRef = new LabOrder();
//            orderRef.setOrderId(orderId);
//            wf.setLabOrder(orderRef);
//
//            // link test
//            var testRef = new Test();
//            testRef.setTestId(test.getTestId());
//            wf.setTest(testRef);
//
//            wf.setStatus(com.labconnect.Enum.WorkflowStatus.PENDING);
//            wf.setAssignedTo(assignedTo);
//            wf.setStartTime(null);
//            wf.setEndTime(null);
//
//            toSave.add(wf);
//        }
//
//        if (toSave.isEmpty()) return List.of();
//
//        return workflowRepo.saveAll(toSave);

}
