package com.labconnect.repository.workFlow;

import com.labconnect.Enum.WorkflowStatus;
import com.labconnect.models.workFlow.TestWorkFlow;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EnableJpaRepositories(basePackages = "com.labconnect.repository.workFlow")
@EntityScan(basePackages = "com.labconnect.models.workFlow")
class WorkFlowRepositoryTest {

    @Autowired
    private TestWorkFlowRepository repository;

    @Test
    void testFindByStatus() {
        // Arrange: create and save workflows
        TestWorkFlow pendingWorkflow = new TestWorkFlow();
        pendingWorkflow.setStatus(WorkflowStatus.PENDING);
        pendingWorkflow.setStartTime(LocalDateTime.now());
        repository.save(pendingWorkflow);

        TestWorkFlow completedWorkflow = new TestWorkFlow();
        completedWorkflow.setStatus(WorkflowStatus.COMPLETED);
        completedWorkflow.setStartTime(LocalDateTime.now());
        repository.save(completedWorkflow);

        // Act: query by status
        List<TestWorkFlow> pendingResults = repository.findByStatus(WorkflowStatus.PENDING);

        // Assert: only pending workflows are returned
        assertThat(pendingResults).hasSize(1);
        assertThat(pendingResults.get(0).getStatus()).isEqualTo(WorkflowStatus.PENDING);
    }
}
