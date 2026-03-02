//package com.labconnect.service;
//
//
//import com.labconnect.Enum.RunStatus;
//import com.labconnect.models.InstrumentRun;
//import com.labconnect.repository.InstrumentRunRepository;
//import com.labconnect.services.InstrumentRunService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.*;
//
//class InstrumentRunServiceTest {
//
//    @Mock
//    private InstrumentRunRepository repository;
//
//    @InjectMocks
//    private InstrumentRunService service;
//
//    private InstrumentRun run;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        run = new InstrumentRun();
//        run.setRunId(1L);
//        run.setInstrumentName("Analyzer X");
//        run.setStatus(RunStatus.PASSED);
//    }
//
//    @Test
//    void testGetAllRuns() {
//        when(repository.findAll()).thenReturn(Arrays.asList(run));
//
//        List<InstrumentRun> runs = service.getAllRuns();
//
//        assertEquals(1, runs.size());
//        assertEquals("Analyzer X", runs.get(0).getInstrumentName());
//        verify(repository, times(1)).findAll();
//    }
//
//    @Test
//    void testCreateRun() {
//        when(repository.save(run)).thenReturn(run);
//
//        InstrumentRun savedRun = service.createRun(run);
//
//        assertNotNull(savedRun);
//        assertEquals(RunStatus.PASSED, savedRun.getStatus());
//        verify(repository, times(1)).save(run);
//    }
//
//    @Test
//    void testUpdateRunStatus() {
//        when(repository.findById(1L)).thenReturn(Optional.of(run));
//        when(repository.save(run)).thenReturn(run);
//
//        InstrumentRun updatedRun = service.updateRunStatus(1L, "COMPLETED");
//
//        assertEquals(RunStatus.FAILED, updatedRun.getStatus());
//        verify(repository, times(1)).findById(1L);
//        verify(repository, times(1)).save(run);
//    }
//}
//
