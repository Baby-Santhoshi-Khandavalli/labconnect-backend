package com.labconnect.repository.testCatalog;
import com.labconnect.models.testCatalog.Test;
import com.labconnect.models.testCatalog.TestParameter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestParameterRepositoryTest {

    @Mock
    private TestParameterRepository parameterRepository;

    private Test sampleTest;
    private List<TestParameter> sampleParameters;

    @BeforeEach
    void setUp() {
        sampleTest = new Test();
        sampleTest.setTestId(101L);
        sampleTest.setName("CBC");

        TestParameter p1 = new TestParameter();
        p1.setName("Hb");
        p1.setTest(sampleTest);

        TestParameter p2 = new TestParameter();
        p2.setName("RBC");
        p2.setTest(sampleTest);

        sampleParameters = List.of(p1, p2);
    }

    @org.junit.jupiter.api.Test
    void findByTest_TestId_returnsParameters() {
        when(parameterRepository.findByTest_TestId(101L)).thenReturn(sampleParameters);
        List<TestParameter> result = parameterRepository.findByTest_TestId(101L);
        assertEquals(2, result.size());
        assertEquals("Hb", result.get(0).getName());
        verify(parameterRepository, times(1)).findByTest_TestId(101L);
    }
}

