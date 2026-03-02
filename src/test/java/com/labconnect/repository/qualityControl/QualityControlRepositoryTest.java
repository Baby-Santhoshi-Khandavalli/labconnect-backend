package com.labconnect.repository.qualityControl;

import com.labconnect.Enum.QCStatus;
import com.labconnect.models.qualityControl.QualityControl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
class QualityControlRepositoryTest {

    @Autowired
    private QualityControlRepository repository;

    @Test
    void testFindByStatus() {
        QualityControl qc = new QualityControl();
        qc.setStatus(QCStatus.PASS);
        repository.save(qc);

        List<QualityControl> result = repository.findByStatus(QCStatus.PASS);

        assertFalse(result.isEmpty());
        assertEquals(QCStatus.PASS, result.get(0).getStatus());
    }
}

