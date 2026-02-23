package com.labconnect.service;
import com.labconnect.DTOResponse.PanelMappingResponse;
import com.labconnect.models.PanelMapping;
import com.labconnect.models.Test;
import com.labconnect.models.TestPanel;
import com.labconnect.repository.PanelMappingRepository;
import com.labconnect.repository.TestPanelRepository;
import com.labconnect.repository.TestRepository;
import com.labconnect.services.PanelMappingService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class PanelMappingServiceTest {
    @Mock private PanelMappingRepository mappingRepo;
    @Mock private TestPanelRepository panelRepo;
    @Mock private TestRepository testRepo;
    @InjectMocks
    private PanelMappingService service;
    @org.junit.jupiter.api.Test
    void getMappingsByPanel_mapsDtos() {
        TestPanel p = new TestPanel(); p.setPanelId(5L); p.setName("LFT");
        Test t = new Test(); t.setTestId(11L); t.setName("ALT");
        PanelMapping m = new PanelMapping();
        m.setMappingId(1L); m.setPanel(p); m.setTest(t);
        when(mappingRepo.findByPanel_PanelId(5L)).thenReturn(List.of(m));
        var out = service.getMappingsByPanel(5L);
        assertEquals(1, out.size());
        assertEquals(1L, out.get(0).getMappingId());
        assertEquals("LFT", out.get(0).getPanelName());
        assertEquals("ALT", out.get(0).getTestName());
    }
    @org.junit.jupiter.api.Test
    void addTestToPanel_whenExists_throws() {
        when(mappingRepo.existsByPanel_PanelIdAndTest_TestId(5L, 11L)).thenReturn(true);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.addTestToPanel(5L, 11L));
        assertTrue(ex.getMessage().contains("already"));
    }
    @org.junit.jupiter.api.Test
    void addTestToPanel_savesAndReturnsDto() {
        when(mappingRepo.existsByPanel_PanelIdAndTest_TestId(5L, 11L)).thenReturn(false);
        TestPanel panel = new TestPanel(); panel.setPanelId(5L); panel.setName("LFT");
        Test test = new Test(); test.setTestId(11L); test.setName("ALT");
        when(panelRepo.findById(5L)).thenReturn(Optional.of(panel));
        when(testRepo.findById(11L)).thenReturn(Optional.of(test));
        PanelMapping saved = new PanelMapping();
        saved.setMappingId(200L); saved.setPanel(panel); saved.setTest(test);
        when(mappingRepo.save(any(PanelMapping.class))).thenReturn(saved);
        PanelMappingResponse out = service.addTestToPanel(5L, 11L);
        assertEquals(200L, out.getMappingId());
        assertEquals(5L, out.getPanelId());
        assertEquals(11L, out.getTestId());
        assertEquals("LFT", out.getPanelName());
        assertEquals("ALT", out.getTestName());
    }
    @org.junit.jupiter.api.Test
    void removeTestFromPanel_invokesDelete() {
        service.removeTestFromPanel(99L);
        verify(mappingRepo).deleteById(99L);
    }
}
