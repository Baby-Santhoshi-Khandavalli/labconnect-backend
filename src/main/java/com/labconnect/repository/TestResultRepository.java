package com.labconnect.repository;

import com.labconnect.Enum.Flag;
import com.labconnect.models.TestResult;
import com.labconnect.models.Identity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Long> {

    // Custom query methods if needed
    List<TestResult> findByWorkflow_WorkflowID(Long workflowId);

    //    List<TestResult> findByParameterId(Long parameterId);
    List<TestResult> findByParameter_ParameterId(Long parameterId);
    List<TestResult> findByFlag(Flag flag);
    @Query("SELECT tr FROM TestResult tr WHERE tr.parameter.parameterId = :pId")
    List<TestResult> findByParameterId(@Param("pId") Long pId);
    List<TestResult> findByEnteredBy(User user);
}
