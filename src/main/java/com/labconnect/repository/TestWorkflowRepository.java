package com.labconnect.repository;

import com.labconnect.Enum.WorkflowStatus;
import com.labconnect.models.TestWorkflow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestWorkflowRepository extends JpaRepository<TestWorkflow, Long> {
    List<TestWorkflow> findByStatus(WorkflowStatus status);
    List<TestWorkflow> findByLabOrder_OrderId(Long orderId);
    List<TestWorkflow> findByTest_TestId(Long testId);
}
