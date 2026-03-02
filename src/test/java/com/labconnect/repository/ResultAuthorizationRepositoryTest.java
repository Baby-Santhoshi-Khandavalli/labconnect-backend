package com.labconnect.repository;
import com.labconnect.models.orderSpecimen.LabOrder;
import com.labconnect.models.ResultAuthorization;
import com.labconnect.models.TestResult;
import com.labconnect.models.TestWorkflow;
import com.labconnect.models.TestParameter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
public class ResultAuthorizationRepositoryTest {

    @Autowired
    private ResultAuthorizationRepository authorizationRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFindByOrderId() {
        // 1. Setup prerequisite entities
        LabOrder order = new LabOrder();
        // Set necessary fields for LabOrder based on your model
        order = entityManager.persistFlushFind(order);

        // We need a TestResult because ResultAuthorization has a OneToOne mapping to it
        TestWorkflow workflow = entityManager.persist(new TestWorkflow());
        TestParameter parameter = entityManager.persist(new TestParameter());

        TestResult result = new TestResult();
        result.setWorkflow(workflow);
        result.setParameter(parameter);
        result = entityManager.persistFlushFind(result);

        // 2. Create and save Authorization
        ResultAuthorization auth = new ResultAuthorization();
        auth.setOrder(order);
        auth.setTestResult(result);
        auth.setPathologist_id(500L);
        auth.setAuthorizedDate(LocalDateTime.now());
        auth.setRemarks("Verified and Approved");

        authorizationRepository.save(auth);

        // 3. Test custom query method
        List<ResultAuthorization> foundList = authorizationRepository.findByOrder_OrderId(order.getOrderId());

        Assertions.assertFalse(foundList.isEmpty());
        Assertions.assertEquals(500L, foundList.get(0).getPathologist_id());
        Assertions.assertEquals("Verified and Approved", foundList.get(0).getRemarks());
    }

    @Test
    public void testFindByPathologistId() {
        // Setup
        LabOrder order = entityManager.persist(new LabOrder());

        ResultAuthorization auth = new ResultAuthorization();
        auth.setOrder(order);
        auth.setPathologist_id(777L);
        auth.setAuthorizedDate(LocalDateTime.now());

        authorizationRepository.save(auth);

        // Test the snake_case mapped method from your repository interface
        List<ResultAuthorization> found = authorizationRepository.findByPathologist_id(777L);

        Assertions.assertNotNull(found);
        Assertions.assertEquals(1, found.size());
        Assertions.assertEquals(777L, found.get(0).getPathologist_id());
    }

    @Test
    public void testFindByAuthorizedDateBetween() {
        LabOrder order = entityManager.persist(new LabOrder());
        LocalDateTime now = LocalDateTime.now();

        ResultAuthorization auth = new ResultAuthorization();
        auth.setOrder(order);
        auth.setAuthorizedDate(now);
        authorizationRepository.save(auth);

        List<ResultAuthorization> results = authorizationRepository.findByAuthorizedDateBetween(
                now.minusDays(1),
                now.plusDays(1)
        );

        Assertions.assertEquals(1, results.size());
    }
}
