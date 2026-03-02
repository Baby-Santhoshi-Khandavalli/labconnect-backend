package com.labconnect.repository.workFlow;


import com.labconnect.Enum.WorkflowStatus;
import com.labconnect.models.workFlow.TestWorkFlow;
import com.labconnect.repository.workFlow.TestWorkFlowRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

//import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
class WorkFlowRepositoryTest {

    @Autowired
    private TestWorkFlowRepository repository;

    @Test
    void testFindByStatus() {
        TestWorkFlow workflow = new TestWorkFlow();
        workflow.setStatus(WorkflowStatus.PENDING);
        repository.save(workflow);

        List<TestWorkFlow> result = repository.findByStatus(WorkflowStatus.PENDING);

        assertFalse(result.isEmpty());
        assertEquals(WorkflowStatus.PENDING, result.get(0).getStatus());
    }
}
