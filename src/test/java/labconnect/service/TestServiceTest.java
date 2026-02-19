package com.labconnect.service;
import com.labconnect.DTORequest.TestRequest;
import com.labconnect.DTOResponse.TestResponse;
import com.labconnect.Enum.Status;
import com.labconnect.mapper.TestMapper;
import com.labconnect.models.Test;
import com.labconnect.repository.TestRepository;
import com.labconnect.services.TestService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class TestServiceTest {
    @Mock private TestRepository testRepository;
    @Mock private TestMapper testMapper;
    @InjectMocks
    private TestService service;
    @org.junit.jupiter.api.Test
    void createTest_defaultsStatusActive_whenNull() {
        TestRequest req = new TestRequest();
        req.setName("CBC");
        Test entity = new Test();
        entity.setName("CBC"); // status null
        when(testMapper.toEntity(req)).thenReturn(entity);
        Test saved = new Test();
        saved.setTestId(1L);
        saved.setName("CBC");
        saved.setStatus(Status.Active);
        when(testRepository.save(argThat(t -> t.getStatus() == Status.Active))).thenReturn(saved);
        TestResponse resp = TestResponse.builder().testId(1L).name("CBC").status("Active").build();
        when(testMapper.toResponseDto(saved)).thenReturn(resp);
        TestResponse out = service.createTest(req);
        assertEquals(1L, out.getTestId());
        assertEquals("CBC", out.getName());
        assertEquals("Active", out.getStatus());
        verify(testRepository).save(any(Test.class));
    }
    @org.junit.jupiter.api.Test
    void updateTest_updatesAndReturnsDto() {
        Long id = 10L;
        TestRequest req = new TestRequest();
        req.setName("CBC - Updated");
        Test existing = new Test();
        existing.setTestId(id);
        existing.setName("CBC");
        when(testRepository.findById(id)).thenReturn(Optional.of(existing));
        doAnswer(inv -> {
            TestRequest r = inv.getArgument(0);
            Test e = inv.getArgument(1);
            if (r.getName() != null) e.setName(r.getName());
            return null;
        }).when(testMapper).updateEntity(eq(req), same(existing));

        when(testRepository.save(existing)).thenReturn(existing);
        when(testMapper.toResponseDto(existing))
                .thenReturn(TestResponse.builder().testId(id).name("CBC - Updated").build());

        TestResponse out = service.updateTest(id, req);
        assertEquals("CBC - Updated", out.getName());
        verify(testMapper).updateEntity(eq(req), same(existing));
    }
    @org.junit.jupiter.api.Test
    void getTestById_returnsDto() {
        Test entity = new Test(); entity.setTestId(5L); entity.setName("Lipid");
        when(testRepository.findById(5L)).thenReturn(Optional.of(entity));
        when(testMapper.toResponseDto(entity))
                .thenReturn(TestResponse.builder().testId(5L).name("Lipid").build());
        TestResponse out = service.getTestById(5L);
        assertEquals(5L, out.getTestId());
        assertEquals("Lipid", out.getName());
    }
    @org.junit.jupiter.api.Test
    void getTestById_notFound_throws() {
        when(testRepository.findById(404L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.getTestById(404L));
        assertTrue(ex.getMessage().contains("Test not found"));
    }
    @org.junit.jupiter.api.Test
    void deactivateTest_setsInactive_andSaves() {
        Test entity = new Test(); entity.setTestId(7L); entity.setStatus(Status.Active);
        when(testRepository.findById(7L)).thenReturn(Optional.of(entity));

        service.deactivateTest(7L);

        assertEquals(Status.Inactive, entity.getStatus());
        verify(testRepository).save(same(entity));
    }
    @org.junit.jupiter.api.Test
    void getActiveTests_mapsList() {
        Test t1 = new Test(); t1.setTestId(1L); t1.setName("CBC"); t1.setStatus(Status.Active);
        Test t2 = new Test(); t2.setTestId(2L); t2.setName("LFT"); t2.setStatus(Status.Active);
        when(testRepository.findByStatus(Status.Active)).thenReturn(List.of(t1, t2));

        when(testMapper.toResponseDto(t1))
                .thenReturn(TestResponse.builder().testId(1L).name("CBC").status("Active").build());
        when(testMapper.toResponseDto(t2))
                .thenReturn(TestResponse.builder().testId(2L).name("LFT").status("Active").build());
        var out = service.getActiveTests();
        assertEquals(2, out.size());
        assertEquals("CBC", out.get(0).getName());
        assertEquals("LFT", out.get(1).getName());
    }
}

