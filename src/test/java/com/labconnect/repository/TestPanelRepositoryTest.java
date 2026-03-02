//package com.labconnect.repository;
//import com.labconnect.models.TestPanel;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import java.util.Optional;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//@ExtendWith(MockitoExtension.class)
//class TestPanelRepositoryTest {
//    @Mock
//    private TestPanelRepository testPanelRepository;
//    private TestPanel samplePanel;
//    @BeforeEach
//    void setUp() {
//        samplePanel = new TestPanel();
//        samplePanel.setPanelId(1L);
//        samplePanel.setName("Complete Blood Count");
//    }
//
//    @Test
//    void findById_shouldReturnPanel_whenPanelExists() {
//        when(testPanelRepository.findById(1L)).thenReturn(Optional.of(samplePanel));
//        Optional<TestPanel> result = testPanelRepository.findById(1L);
//        assertTrue(result.isPresent());
//        assertEquals("Complete Blood Count", result.get().getName());
//
//        verify(testPanelRepository, times(1)).findById(1L);
//    }
//    @Test
//    void save_shouldReturnSavedPanel() {
//
//        when(testPanelRepository.save(any(TestPanel.class))).thenReturn(samplePanel);
//        TestPanel savedPanel = testPanelRepository.save(new TestPanel());
//        assertNotNull(savedPanel);
//        assertEquals(1L, savedPanel.getPanelId());
//        verify(testPanelRepository).save(any(TestPanel.class));
//    }
//}
//
