package com.labconnect.repository;

import com.labconnect.Enum.WorkflowStatus;
import com.labconnect.models.TestWorkflow;
import com.labconnect.repository.TestWorkflowRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
class WorkflowRepositoryTest {

    @Autowired
    private TestWorkflowRepository repository;

    @Test
    void testFindByStatus() {
        TestWorkflow workflow = new TestWorkflow();
        workflow.setStatus(WorkflowStatus.PENDING);
        repository.save(workflow);

        List<TestWorkflow> result = repository.findByStatus(WorkflowStatus.PENDING);

        assertFalse(result.isEmpty());
        assertEquals(WorkflowStatus.PENDING, result.get(0).getStatus());
    }
}
