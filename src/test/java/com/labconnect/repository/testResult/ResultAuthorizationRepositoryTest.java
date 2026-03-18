package com.labconnect.repository.testResult;

import com.labconnect.models.Identity.User;
import com.labconnect.models.orderSpecimen.LabOrder;
import com.labconnect.models.testCatalog.TestParameter;
import com.labconnect.models.testResult.ResultAuthorization;
import com.labconnect.models.testResult.TestResult;
import com.labconnect.models.workFlow.TestWorkFlow;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ResultAuthorizationRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private ResultAuthorizationRepository repository;

    @Test
    @DisplayName("findByAuthorizedDateBetween: returns items within the window")
    void findByAuthorizedDateBetween_returnsExpectedRows() {
        TestWorkFlow wf = em.persistFlushFind(new TestWorkFlow());
        TestParameter param = em.persistFlushFind(new TestParameter());
        User technologist = em.persistFlushFind(new User());
        User pathologist = em.persistFlushFind(new User());
        User clinician = em.persistFlushFind(new User());

        TestResult result = new TestResult();
        result.setWorkflow(wf);
        result.setParameter(param);
        result.setValue("7.2");
        result.setEnteredDate(LocalDateTime.now().minusDays(1));
        result.setEnteredBy(technologist);
        result = em.persistFlushFind(result);

        LabOrder order = new LabOrder();
        // Match your LabOrder field name:
        order.setClinicianId(clinician);
        // If LabOrder has other non-null fields (status/priority/orderDate), set minimal values here.
        order = em.persistFlushFind(order);

        ResultAuthorization inside = new ResultAuthorization();
        inside.setTestResult(result);
        inside.setOrder(order);
        inside.setPathologist(pathologist);
        inside.setAuthorizedDate(LocalDateTime.now().minusHours(2));
        inside.setRemarks("inside");
        em.persist(inside);

        ResultAuthorization outside = new ResultAuthorization();
        outside.setTestResult(result);
        outside.setOrder(order);
        outside.setPathologist(pathologist);
        outside.setAuthorizedDate(LocalDateTime.now().minusDays(3));
        outside.setRemarks("outside");
        em.persist(outside);

        em.flush();
        em.clear();

        LocalDateTime start = LocalDateTime.now().minusHours(6);
        LocalDateTime end   = LocalDateTime.now();

        List<ResultAuthorization> found = repository.findByAuthorizedDateBetween(start, end);

        assertThat(found).hasSize(1);
        assertThat(found.get(0).getRemarks()).isEqualTo("inside");
        assertThat(found.get(0).getAuthorizedDate()).isBetween(start, end);
    }
}