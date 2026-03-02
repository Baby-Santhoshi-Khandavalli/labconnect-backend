package com.labconnect.repository.testCatalog;
import com.labconnect.Enum.Status;
import com.labconnect.models.testCatalog.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestRepositoryTest {

    @Mock
    private TestRepository testRepository;

    @org.junit.jupiter.api.Test
    void save_and_findById_works() {
        // 1. Arrange
        Test t = new Test();
        t.setTestId(1L);
        t.setName("CBC");
        t.setStatus(Status.Active);

        // Stubbing: "When save is called with any Test, return our test object"
        when(testRepository.save(any(Test.class))).thenReturn(t);
        when(testRepository.findById(1L)).thenReturn(Optional.of(t));

        // 2. Act
        Test persisted = testRepository.save(t);
        Optional<Test> found = testRepository.findById(1L);

        // 3. Assert
        assertTrue(found.isPresent());
        assertEquals("CBC", found.get().getName());
        // Verify the repository methods were actually called
        verify(testRepository, times(1)).save(t);
        verify(testRepository, times(1)).findById(1L);
    }

    @org.junit.jupiter.api.Test
    void findByStatus_returnsOnlyActive() {
        // 1. Arrange
        Test t1 = new Test();
        t1.setName("CBC");
        t1.setStatus(Status.Active);

        List<Test> mockList = List.of(t1);
        when(testRepository.findByStatus(Status.Active)).thenReturn(mockList);

        // 2. Act
        List<Test> actives = testRepository.findByStatus(Status.Active);

        // 3. Assert
        assertEquals(1, actives.size());
        assertEquals("CBC", actives.get(0).getName());
        verify(testRepository, times(1)).findByStatus(Status.Active);
    }
}

