package com.labconnect.service.testCatalog;
import com.labconnect.DTORequest.testCatalog.TestParameterRequest;
import com.labconnect.DTOResponse.testCatalog.TestParameterResponse;
import com.labconnect.mapper.testCatalog.TestParameterMapper;
import com.labconnect.models.testCatalog.Test;
import com.labconnect.models.testCatalog.TestParameter;
import com.labconnect.repository.testCatalog.TestParameterRepository;
import com.labconnect.repository.testCatalog.TestRepository;
import com.labconnect.services.testCatalog.TestParameterService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestParameterServiceTest {
    @Mock private TestParameterRepository parameterRepository;
    @Mock private TestRepository testRepository;
    @Mock private TestParameterMapper mapper;
    @InjectMocks
    private TestParameterService service;

    @org.junit.jupiter.api.Test

    void addParameterToTest_missingTestId_throws() {
        TestParameterRequest req = new TestParameterRequest();
        req.setName("Hb");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.addParameterToTest(req));
        assertTrue(ex.getMessage().contains("testId is required"));
    }
    @org.junit.jupiter.api.Test
    void addParameterToTest_ok() {
        TestParameterRequest req = new TestParameterRequest();
        req.setTestId(1L); req.setName("Hb");
        Test test = new Test(); test.setTestId(1L);
        when(testRepository.findById(1L)).thenReturn(Optional.of(test));
        TestParameter entity = new TestParameter(); entity.setName("Hb"); entity.setTest(test);
        when(mapper.toEntity(req, test)).thenReturn(entity);
        TestParameter saved = new TestParameter(); saved.setParameterId(101L); saved.setName("Hb"); saved.setTest(test);
        when(parameterRepository.save(entity)).thenReturn(saved);
        TestParameterResponse dto = new TestParameterResponse();
        dto.setParameterId(101L); dto.setName("Hb"); dto.setTestId(1L);
        when(mapper.toResponse(saved)).thenReturn(dto);
        TestParameterResponse out = service.addParameterToTest(req);
        assertEquals(101L, out.getParameterId());
        assertEquals("Hb", out.getName());
    }
    @org.junit.jupiter.api.Test
    void updateParameter_relinkWhenTestIdPresent() {
        Long paramId = 55L;
        TestParameter existing = new TestParameter(); existing.setParameterId(paramId);
        when(parameterRepository.findById(paramId)).thenReturn(Optional.of(existing));

        TestParameterRequest req = new TestParameterRequest();
        req.setTestId(2L); req.setName("Platelets");

        Test newTest = new Test(); newTest.setTestId(2L);
        when(testRepository.findById(2L)).thenReturn(Optional.of(newTest));

        doAnswer(inv -> {
            TestParameterRequest r = inv.getArgument(0);
            TestParameter e = inv.getArgument(1);
            if (r.getName() != null) e.setName(r.getName());
            return null;
        }).when(mapper).updateEntity(eq(req), same(existing));

        TestParameter saved = new TestParameter();
        saved.setParameterId(paramId); saved.setName("Platelets"); saved.setTest(newTest);
        when(parameterRepository.save(existing)).thenReturn(saved);

        TestParameterResponse dto = new TestParameterResponse();
        dto.setParameterId(paramId); dto.setName("Platelets"); dto.setTestId(2L);
        when(mapper.toResponse(saved)).thenReturn(dto);

        TestParameterResponse out = service.updateParameter(paramId, req);
        assertEquals(2L, out.getTestId());
        assertEquals("Platelets", out.getName());
        verify(mapper).updateEntity(eq(req), same(existing));
    }

    @org.junit.jupiter.api.Test
    void deleteParameter_invokesRepo() {
        service.deleteParameter(99L);
        verify(parameterRepository).deleteById(99L);
    }



}




