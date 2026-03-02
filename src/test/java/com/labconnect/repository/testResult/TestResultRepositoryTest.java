package com.labconnect.repository.testResult;


import com.labconnect.Enum.Flag;
import com.labconnect.models.testCatalog.TestParameter;
import com.labconnect.models.testResult.TestResult;
import com.labconnect.models.workFlow.TestWorkFlow;
import com.labconnect.models.Identity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
public class TestResultRepositoryTest {

    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testSaveAndFindByFlag() {
        // Arrange: persist required associations

        // Persist a workflow (set required fields if your entity has NOT NULL constraints)
        TestWorkFlow workflow = new TestWorkFlow();
        // e.g., workflow.setStatus(...); workflow.setStage(...); etc.
        workflow = entityManager.persistAndFlush(workflow);

        // Persist a parameter (set required fields if needed)
        TestParameter parameter = new TestParameter();
        // e.g., parameter.setName("Glucose"); parameter.setUnit("mg/dL"); etc.
        parameter = entityManager.persistAndFlush(parameter);

        // Persist a user who "entered" the result (since enteredBy is a User relation)
        User technician = new User();
        technician.setName("Tech Alpha");
        technician.setEmail("tech.alpha@example.com");
        technician.setRole(User.Role.Technician);
        technician = entityManager.persistAndFlush(technician);

        // Build and save the TestResult
        TestResult result = new TestResult();
        result.setWorkflow(workflow);
        result.setParameter(parameter);
        result.setValue("14.5");
        result.setFlag(Flag.HIGH);

        // ✅ enteredBy is a User relation now
        result.setEnteredBy(technician);

        result.setEnteredDate(LocalDateTime.now());

        testResultRepository.saveAndFlush(result);

        // Act
        List<TestResult> found = testResultRepository.findByFlag(Flag.HIGH);

        // Assert
        Assertions.assertFalse(found.isEmpty());
        Assertions.assertEquals("14.5", found.get(0).getValue());

        // You can also assert the relation:
        Assertions.assertNotNull(found.get(0));
    }
}