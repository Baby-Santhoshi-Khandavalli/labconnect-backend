package com.labconnect.repository;
import com.labconnect.Enum.Flag;
import com.labconnect.models.TestParameter;
import com.labconnect.models.TestResult;
import com.labconnect.models.TestWorkflow;
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
        // Mocking dependencies manually for the DB context
        TestWorkflow workflow = new TestWorkflow(); // Ensure mandatory fields are set
        entityManager.persist(workflow);

        TestParameter parameter = new TestParameter();
        entityManager.persist(parameter);

        TestResult result = new TestResult();
        result.setWorkflow(workflow);
        result.setParameter(parameter);
        result.setValue("14.5");
        result.setFlag(Flag.HIGH);
        result.setEnteredBy("Tech_Alpha");
        result.setEnteredDate(LocalDateTime.now());

        testResultRepository.save(result);

        List<TestResult> found = testResultRepository.findByFlag(Flag.HIGH);

        Assertions.assertFalse(found.isEmpty());
        Assertions.assertEquals("14.5", found.get(0).getValue());
        Assertions.assertEquals("Tech_Alpha", found.get(0).getEnteredBy());
    }
}