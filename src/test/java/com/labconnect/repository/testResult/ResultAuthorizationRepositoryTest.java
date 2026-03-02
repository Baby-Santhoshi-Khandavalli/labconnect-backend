//package com.labconnect.repository.testResult;
//import com.labconnect.models.orderSpecimen.LabOrder;
//import com.labconnect.models.testResult.ResultAuthorization;
//import com.labconnect.models.testResult.TestResult;
//import com.labconnect.models.workFlow.TestWorkFlow;
//import com.labconnect.models.testCatalog.TestParameter;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@DataJpaTest
//public class ResultAuthorizationRepositoryTest {
//
//    @Autowired
//    private ResultAuthorizationRepository authorizationRepository;
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Test
//    public void testFindByOrderId() {
//        // 1. Setup prerequisite entities
//        LabOrder order = new LabOrder();
//        // Set necessary fields for LabOrder based on your model
//        order = entityManager.persistFlushFind(order);
//
//        // We need a TestResult because ResultAuthorization has a OneToOne mapping to it
//        TestWorkFlow workflow = entityManager.persist(new TestWorkFlow());
//        TestParameter parameter = entityManager.persist(new TestParameter());
//
//        TestResult result = new TestResult();
//        result.setWorkflow(workflow);
//        result.setParameter(parameter);
//        result = entityManager.persistFlushFind(result);
//
//        // 2. Create and save Authorization
//        ResultAuthorization auth = new ResultAuthorization();
//        auth.setOrder(order);
//        auth.setTestResult(result);
//        auth.setPathologist_id(500L);
//        auth.setAuthorizedDate(LocalDateTime.now());
//        auth.setRemarks("Verified and Approved");
//
//        authorizationRepository.save(auth);
//
//        // 3. Test custom query method
//        List<ResultAuthorization> foundList = authorizationRepository.findByOrder_OrderId(order.getOrderId());
//
//        Assertions.assertFalse(foundList.isEmpty());
//        Assertions.assertEquals(500L, foundList.get(0).getPathologist_id());
//        Assertions.assertEquals("Verified and Approved", foundList.get(0).getRemarks());
//    }
//
//    @Test
//    public void testFindByPathologistId() {
//        // Setup
//        LabOrder order = entityManager.persist(new LabOrder());
//
//        ResultAuthorization auth = new ResultAuthorization();
//        auth.setOrder(order);
//        auth.setPathologist_id(777L);
//        auth.setAuthorizedDate(LocalDateTime.now());
//
//        authorizationRepository.save(auth);
//
//        // Test the snake_case mapped method from your repository interface
//        List<ResultAuthorization> found = authorizationRepository.findByPathologist_id(777L);
//
//        Assertions.assertNotNull(found);
//        Assertions.assertEquals(1, found.size());
//        Assertions.assertEquals(777L, found.get(0).getPathologist_id());
//    }
//
//    @Test
//    public void testFindByAuthorizedDateBetween() {
//        LabOrder order = entityManager.persist(new LabOrder());
//        LocalDateTime now = LocalDateTime.now();
//
//        ResultAuthorization auth = new ResultAuthorization();
//        auth.setOrder(order);
//        auth.setAuthorizedDate(now);
//        authorizationRepository.save(auth);
//
//        List<ResultAuthorization> results = authorizationRepository.findByAuthorizedDateBetween(
//                now.minusDays(1),
//                now.plusDays(1)
//        );
//
//        Assertions.assertEquals(1, results.size());
//    }
//}



package com.labconnect.repository.testResult;
//package com.labconnect.repository;

//import com.labconnect.models.*;
import com.labconnect.models.Identity.User;
import com.labconnect.models.orderSpecimen.LabOrder;
import com.labconnect.models.testCatalog.TestParameter;
import com.labconnect.models.testResult.ResultAuthorization;
import com.labconnect.models.testResult.TestResult;
import com.labconnect.models.workFlow.TestWorkFlow;
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
        // 1) Persist minimal LabOrder
        LabOrder order = entityManager.persistFlushFind(new LabOrder());

        // 2) Persist a Pathologist user (relation required for setPathologist(User))
        User pathologist = new User();
        pathologist.setName("Dr. Patho");
        pathologist.setRole(User.Role.Pathologist);
        pathologist = entityManager.persistFlushFind(pathologist);

        // 3) Persist minimal TestResult (required due to nullable=false on result_id)
        TestWorkFlow workflow = entityManager.persist(new TestWorkFlow());
        TestParameter parameter = entityManager.persist(new TestParameter());

        TestResult result = new TestResult();
        result.setWorkflow(workflow);
        result.setParameter(parameter);
        result = entityManager.persistFlushFind(result);

        // 4) Create and save ResultAuthorization
        ResultAuthorization auth = new ResultAuthorization();
        auth.setOrder(order);
        auth.setTestResult(result);
        auth.setPathologist(pathologist); // <-- pass User, not an ID
        auth.setAuthorizedDate(LocalDateTime.now());
        auth.setRemarks("Verified and Approved");

        authorizationRepository.saveAndFlush(auth);

        // 5) Query using concatenated nested property (order.orderId)
        List<ResultAuthorization> foundList =
                authorizationRepository.findByOrderOrderId(order.getOrderId());

        Assertions.assertFalse(foundList.isEmpty(), "Expected at least one authorization");
        Assertions.assertEquals(pathologist.getUserId(), foundList.get(0).getPathologist().getUserId());
        Assertions.assertEquals("Verified and Approved", foundList.get(0).getRemarks());
    }

    @Test
    public void testFindByPathologistId() {
        // 1) Persist minimal LabOrder
        LabOrder order = entityManager.persist(new LabOrder());

        // 2) Pathologist user
        User pathologist = new User();
        pathologist.setName("Dr. P");
        pathologist.setRole(User.Role.Pathologist);
        pathologist = entityManager.persistFlushFind(pathologist);

        // 3) Minimal TestResult (due to nullable=false on result_id)
        TestWorkFlow workflow = entityManager.persist(new TestWorkFlow());
        TestParameter parameter = entityManager.persist(new TestParameter());

        TestResult result = new TestResult();
        result.setWorkflow(workflow);
        result.setParameter(parameter);
        result = entityManager.persistFlushFind(result);

        // 4) Save authorization
        ResultAuthorization auth = new ResultAuthorization();
        auth.setOrder(order);
        auth.setTestResult(result);
        auth.setPathologist(pathologist);
        auth.setAuthorizedDate(LocalDateTime.now());

        authorizationRepository.saveAndFlush(auth);

        // 5) Query by concatenated nested property (pathologist.userId)
        List<ResultAuthorization> found =
                authorizationRepository.findByPathologistUserId(pathologist.getUserId());

        Assertions.assertNotNull(found);
        Assertions.assertEquals(1, found.size());
        Assertions.assertEquals(pathologist.getUserId(), found.get(0).getPathologist().getUserId());
    }

    @Test
    public void testFindByAuthorizedDateBetween() {
        LocalDateTime now = LocalDateTime.now();

        // 1) Persist minimal LabOrder
        LabOrder order = entityManager.persist(new LabOrder());

        // 2) Pathologist user
        User pathologist = new User();
        pathologist.setName("Dr. Q");
        pathologist.setRole(User.Role.Pathologist);
        pathologist = entityManager.persistFlushFind(pathologist);

        // 3) Minimal TestResult (required)
        TestWorkFlow workflow = entityManager.persist(new TestWorkFlow());
        TestParameter parameter = entityManager.persist(new TestParameter());

        TestResult result = new TestResult();
        result.setWorkflow(workflow);
        result.setParameter(parameter);
        result = entityManager.persistFlushFind(result);

        // 4) Persist ResultAuthorization with current time
        ResultAuthorization auth = new ResultAuthorization();
        auth.setOrder(order);
        auth.setTestResult(result);
        auth.setPathologist(pathologist);
        auth.setAuthorizedDate(now);

        authorizationRepository.saveAndFlush(auth);

        // 5) Query date range
        List<ResultAuthorization> results = authorizationRepository.findByAuthorizedDateBetween(
                now.minusDays(1),
                now.plusDays(1)
        );

        Assertions.assertEquals(1, results.size());
    }
}