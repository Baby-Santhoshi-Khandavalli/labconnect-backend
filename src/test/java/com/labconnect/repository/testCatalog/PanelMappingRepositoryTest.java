package com.labconnect.repository.testCatalog;
import com.labconnect.models.testCatalog.PanelMapping;
import com.labconnect.models.testCatalog.TestPanel;
import com.labconnect.models.testCatalog.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PanelMappingRepositoryTest {

    @Mock
    private PanelMappingRepository panelMappingRepository;

    private PanelMapping mapping1;
    private PanelMapping mapping2;

    @BeforeEach
    void setUp() {
        TestPanel panel = new TestPanel();
        panel.setPanelId(5L);
        panel.setName("LFT");

        Test t1 = new Test();
        t1.setTestId(11L);
        t1.setName("ALT");

        Test t2 = new Test();
        t2.setTestId(12L);
        t2.setName("AST");

        mapping1 = new PanelMapping();
        mapping1.setMappingId(100L);
        mapping1.setPanel(panel);
        mapping1.setTest(t1);

        mapping2 = new PanelMapping();
        mapping2.setMappingId(101L);
        mapping2.setPanel(panel);
        mapping2.setTest(t2);
    }

    @org.junit.jupiter.api.Test
    void findByPanel_PanelId_shouldReturnList_whenMappingsExist() {
        when(panelMappingRepository.findByPanel_PanelId(5L))
                .thenReturn(List.of(mapping1, mapping2));

        List<PanelMapping> result = panelMappingRepository.findByPanel_PanelId(5L);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(5L, result.get(0).getPanel().getPanelId());
        assertEquals(5L, result.get(1).getPanel().getPanelId());

        verify(panelMappingRepository, times(1)).findByPanel_PanelId(5L);
        verify(panelMappingRepository, never()).existsByPanel_PanelIdAndTest_TestId(anyLong(), anyLong());
    }

    @org.junit.jupiter.api.Test
    void findByPanel_PanelId_shouldReturnEmptyList_whenNoMappings() {
        when(panelMappingRepository.findByPanel_PanelId(999L)).thenReturn(List.of());

        List<PanelMapping> result = panelMappingRepository.findByPanel_PanelId(999L);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(panelMappingRepository).findByPanel_PanelId(999L);
    }

    @org.junit.jupiter.api.Test
    void existsByPanel_PanelIdAndTest_TestId_shouldReturnTrue_whenMappingExists() {
        when(panelMappingRepository.existsByPanel_PanelIdAndTest_TestId(5L, 11L))
                .thenReturn(true);

        boolean exists = panelMappingRepository.existsByPanel_PanelIdAndTest_TestId(5L, 11L);

        assertTrue(exists);
        verify(panelMappingRepository).existsByPanel_PanelIdAndTest_TestId(5L, 11L);
    }

    @org.junit.jupiter.api.Test
    void existsByPanel_PanelIdAndTest_TestId_shouldReturnFalse_whenMappingNotExists() {
        when(panelMappingRepository.existsByPanel_PanelIdAndTest_TestId(5L, 99L))
                .thenReturn(false);
        boolean exists = panelMappingRepository.existsByPanel_PanelIdAndTest_TestId(5L, 99L);
        assertFalse(exists);
        verify(panelMappingRepository).existsByPanel_PanelIdAndTest_TestId(5L, 99L);
    }
}