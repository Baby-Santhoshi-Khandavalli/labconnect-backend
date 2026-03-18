package com.labconnect.repository.testResult;

import com.labconnect.Enum.Flag;
import com.labconnect.models.Identity.User;
import com.labconnect.models.testCatalog.TestParameter;
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
class TestResultRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private TestResultRepository repository;

    @Test
    @DisplayName("findByFlag: returns results matching the given flag")
    void findByFlag_returnsExpectedRows() {
        TestWorkFlow wf = em.persistFlushFind(new TestWorkFlow());
        TestParameter param = em.persistFlushFind(new TestParameter());
        User user = em.persistFlushFind(new User());

        TestResult normal = new TestResult();
        normal.setWorkflow(wf);
        normal.setParameter(param);
        normal.setValue("10.0");
        normal.setFlag(Flag.NORMAL);
        normal.setEnteredDate(LocalDateTime.now().minusHours(3));
        normal.setEnteredBy(user);
        em.persist(normal);

        TestResult high = new TestResult();
        high.setWorkflow(wf);
        high.setParameter(param);
        high.setValue("20.0");
        high.setFlag(Flag.HIGH);
        high.setEnteredDate(LocalDateTime.now().minusHours(2));
        high.setEnteredBy(user);
        em.persist(high);

        em.flush();
        em.clear();

        List<TestResult> results = repository.findByFlag(Flag.HIGH);

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getFlag()).isEqualTo(Flag.HIGH);
        assertThat(results.get(0).getValue()).isEqualTo("20.0");
    }
}