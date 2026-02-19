package com.labconnect.repository;
import com.labconnect.models.PanelMapping;
import com.labconnect.models.Test;
import com.labconnect.models.TestPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PanelMappingRepositoryTest {
    @Mock
    private PanelMappingRepository mappingRepository;
    private TestPanel lftPanel;
    private Test altTest;
    private Test astTest;

    @BeforeEach
    void setUp() {
        lftPanel = new TestPanel();
        lftPanel.setPanelId(1L);
        lftPanel.setName("LFT");
        altTest = new Test();
        altTest.setTestId(10L);
        altTest.setName("ALT");
        astTest = new Test();
        astTest.setTestId(11L);
        astTest.setName("AST");
    }

    @org.junit.jupiter.api.Test
    void findByPanel_PanelId_returnsMappings() {

        PanelMapping m1 = new PanelMapping(); m1.setPanel(lftPanel); m1.setTest(altTest);
        PanelMapping m2 = new PanelMapping(); m2.setPanel(lftPanel); m2.setTest(astTest);
        when(mappingRepository.findByPanel_PanelId(1L)).thenReturn(List.of(m1, m2));
        List<PanelMapping> found = mappingRepository.findByPanel_PanelId(1L);
        assertEquals(2, found.size());
        assertTrue(found.stream().anyMatch(pm -> "ALT".equals(pm.getTest().getName())));
        verify(mappingRepository, times(1)).findByPanel_PanelId(1L);
    }

    @org.junit.jupiter.api.Test
    void existsByPanel_PanelIdAndTest_TestId_detectsDuplicate() {
        when(mappingRepository.existsByPanel_PanelIdAndTest_TestId(1L, 10L)).thenReturn(true);
        boolean exists = mappingRepository.existsByPanel_PanelIdAndTest_TestId(1L, 10L);
        assertTrue(exists);
        verify(mappingRepository, times(1)).existsByPanel_PanelIdAndTest_TestId(1L, 10L);
    }
}

