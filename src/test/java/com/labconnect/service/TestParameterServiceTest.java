//package com.labconnect.service;
//import com.labconnect.DTORequest.TestParameterRequest;
//import com.labconnect.DTOResponse.TestParameterResponse;
//import com.labconnect.mapper.TestParameterMapper;
//import com.labconnect.models.Test;
//import com.labconnect.models.TestParameter;
//import com.labconnect.repository.TestParameterRepository;
//import com.labconnect.repository.TestRepository;
//import com.labconnect.services.TestParameterService;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.*;
//import org.mockito.junit.jupiter.MockitoExtension;
//import java.util.List;
//import java.util.Optional;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class TestParameterServiceTest {
//    @Mock private TestParameterRepository parameterRepository;
//    @Mock private TestRepository testRepository;
//    @Mock private TestParameterMapper mapper;
//    @InjectMocks
//    private TestParameterService service;
//
//    @org.junit.jupiter.api.Test
//
//    void addParameterToTest_missingTestId_throws() {
//        TestParameterRequest req = new TestParameterRequest();
//        req.setName("Hb");
//        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
//                () -> service.addParameterToTest(req));
//        assertTrue(ex.getMessage().contains("testId is required"));
//    }
//    @org.junit.jupiter.api.Test
//    void addParameterToTest_ok() {
//        TestParameterRequest req = new TestParameterRequest();
//        req.setTestId(1L); req.setName("Hb");
//        Test test = new Test(); test.setTestId(1L);
//        when(testRepository.findById(1L)).thenReturn(Optional.of(test));
//        TestParameter entity = new TestParameter(); entity.setName("Hb"); entity.setTest(test);
//        when(mapper.toEntity(req, test)).thenReturn(entity);
//        TestParameter saved = new TestParameter(); saved.setParameterId(101L); saved.setName("Hb"); saved.setTest(test);
//        when(parameterRepository.save(entity)).thenReturn(saved);
//        TestParameterResponse dto = new TestParameterResponse();
//        dto.setParameterId(101L); dto.setName("Hb"); dto.setTestId(1L);
//        when(mapper.toResponse(saved)).thenReturn(dto);
//        TestParameterResponse out = service.addParameterToTest(req);
//        assertEquals(101L, out.getParameterId());
//        assertEquals("Hb", out.getName());
//    }
//    @org.junit.jupiter.api.Test
//    void updateParameter_relinkWhenTestIdPresent() {
//        Long paramId = 55L;
//        TestParameter existing = new TestParameter(); existing.setParameterId(paramId);
//        when(parameterRepository.findById(paramId)).thenReturn(Optional.of(existing));
//
//        TestParameterRequest req = new TestParameterRequest();
//        req.setTestId(2L); req.setName("Platelets");
//
//        Test newTest = new Test(); newTest.setTestId(2L);
//        when(testRepository.findById(2L)).thenReturn(Optional.of(newTest));
//
//        doAnswer(inv -> {
//            TestParameterRequest r = inv.getArgument(0);
//            TestParameter e = inv.getArgument(1);
//            if (r.getName() != null) e.setName(r.getName());
//            return null;
//        }).when(mapper).updateEntity(eq(req), same(existing));
//
//        TestParameter saved = new TestParameter();
//        saved.setParameterId(paramId); saved.setName("Platelets"); saved.setTest(newTest);
//        when(parameterRepository.save(existing)).thenReturn(saved);
//
//        TestParameterResponse dto = new TestParameterResponse();
//        dto.setParameterId(paramId); dto.setName("Platelets"); dto.setTestId(2L);
//        when(mapper.toResponse(saved)).thenReturn(dto);
//
//        TestParameterResponse out = service.updateParameter(paramId, req);
//        assertEquals(2L, out.getTestId());
//        assertEquals("Platelets", out.getName());
//        verify(mapper).updateEntity(eq(req), same(existing));
//    }
//    @org.junit.jupiter.api.Test
//    void getParametersByTestId_mapsList() {
//        TestParameter p1 = new TestParameter(); p1.setParameterId(1L);
//        TestParameter p2 = new TestParameter(); p2.setParameterId(2L);
//        when(parameterRepository.findByTest_TestId(10L)).thenReturn(List.of(p1, p2));
//        TestParameterResponse r1 = new TestParameterResponse(); r1.setParameterId(1L);
//        TestParameterResponse r2 = new TestParameterResponse(); r2.setParameterId(2L);
//        when(mapper.toResponse(p1)).thenReturn(r1);
//        when(mapper.toResponse(p2)).thenReturn(r2);
//        var out = service.getParametersByTestId(10L);
//        assertEquals(2, out.size());
//        assertEquals(2L, out.get(1).getParameterId());
//    }
//    @org.junit.jupiter.api.Test
//    void deleteParameter_invokesRepo() {
//        service.deleteParameter(99L);
//        verify(parameterRepository).deleteById(99L);
//    }
//    @org.junit.jupiter.api.Test
//    void findByTest_TestId_delegates() {
//        TestParameter p = new TestParameter(); p.setParameterId(7L);
//        when(parameterRepository.findByTest_TestId(20L)).thenReturn(List.of(p));
//        when(mapper.toResponse(p)).thenAnswer(inv -> {
//            TestParameter e = inv.getArgument(0);
//            TestParameterResponse r = new TestParameterResponse();
//            r.setParameterId(e.getParameterId());
//            return r;
//        });
//        var out = service.findByTest_TestId(20L);
//        assertEquals(1, out.size());
//        assertEquals(7L, out.get(0).getParameterId());
//    }
//}
//
