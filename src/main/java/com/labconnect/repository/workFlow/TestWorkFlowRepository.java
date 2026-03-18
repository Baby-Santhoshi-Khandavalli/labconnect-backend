package com.labconnect.repository.workFlow;

import com.labconnect.Enum.WorkflowStatus;

import com.labconnect.models.workFlow.TestWorkFlow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestWorkFlowRepository extends JpaRepository<TestWorkFlow, Long> {
    List<TestWorkFlow> findByStatus(WorkflowStatus status);

}
