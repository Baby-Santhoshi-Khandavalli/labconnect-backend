package com.labconnect.service;


import com.labconnect.Enum.WorkflowStatus;
import com.labconnect.exceptions.ResourceNotFoundException;
import com.labconnect.models.TestWorkflow;
import com.labconnect.repository.TestWorkflowRepository;
import com.labconnect.services.TestWorkflowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkflowServiceTest {

    @Mock
    private TestWorkflowRepository repository;

    @InjectMocks
    private TestWorkflowService service;

    private TestWorkflow workflow;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        workflow = new TestWorkflow();
        workflow.setWorkflowID(1L);
        workflow.setStatus(WorkflowStatus.PENDING);
    }

    @Test
    void testCreateWorkflow() {
        when(repository.save(workflow)).thenReturn(workflow);

        TestWorkflow savedWorkflow = service.createWorkflow(workflow);

        assertNotNull(savedWorkflow);
        assertEquals(WorkflowStatus.PENDING, savedWorkflow.getStatus());
        verify(repository, times(1)).save(workflow);
    }

    @Test
    void testUpdateWorkflowStatus() {
        when(repository.findById(1L)).thenReturn(Optional.of(workflow));
        when(repository.save(workflow)).thenReturn(workflow);

        TestWorkflow updatedWorkflow = service.updateWorkflowStatus(1L, "COMPLETED");

        assertEquals(WorkflowStatus.COMPLETED, updatedWorkflow.getStatus());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(workflow);
    }

    @Test
    void testUpdateWorkflowStatus_NotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.updateWorkflowStatus(99L, "COMPLETED"));
    }
}
