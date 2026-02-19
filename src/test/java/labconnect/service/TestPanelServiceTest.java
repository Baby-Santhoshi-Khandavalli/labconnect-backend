package com.labconnect.service;
import com.labconnect.DTORequest.TestPanelRequest;
import com.labconnect.DTOResponse.TestPanelResponse;
import com.labconnect.models.Test;
import com.labconnect.models.TestPanel;
import com.labconnect.repository.PanelMappingRepository;
import com.labconnect.repository.TestPanelRepository;
import com.labconnect.repository.TestRepository;
import com.labconnect.services.TestPanelService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestPanelServiceTest {

    @Mock private TestPanelRepository panelRepo;
    @Mock private TestRepository testRepo;
    @Mock private PanelMappingRepository mappingRepo;

    @InjectMocks
    private TestPanelService service;
    @org.junit.jupiter.api.Test
    void createPanel_withTestIds_buildsMappingsAndReturnsDto() {
        TestPanelRequest req = new TestPanelRequest();
        req.setName("Renal Panel");
        req.setDescription("Kidney tests");
        req.setTestIds(List.of(101L, 102L));

        TestPanel savedPanel = new TestPanel();
        savedPanel.setPanelId(1L);
        savedPanel.setName("Renal Panel");
        savedPanel.setDescription("Kidney tests");
        when(panelRepo.save(any(TestPanel.class))).thenReturn(savedPanel);

        Test t1 = new Test(); t1.setTestId(101L); t1.setName("Urea");
        Test t2 = new Test(); t2.setTestId(102L); t2.setName("Creatinine");
        when(testRepo.findById(101L)).thenReturn(Optional.of(t1));
        when(testRepo.findById(102L)).thenReturn(Optional.of(t2));

        when(mappingRepo.saveAll(anyList())).thenAnswer(inv -> inv.getArgument(0));

        TestPanelResponse out = service.createPanel(req);

        assertEquals(1L, out.getPanelId());
        assertEquals("Renal Panel", out.getName());
        assertTrue(out.getTestNames().contains("Urea"));
        assertTrue(out.getTestNames().contains("Creatinine"));
        verify(panelRepo).save(any(TestPanel.class));
        verify(mappingRepo).saveAll(anyList());
    }
    @org.junit.jupiter.api.Test

    void updatePanel_updatesNameAndDescription() {
        Long id = 77L;
        TestPanel existing = new TestPanel();
        existing.setPanelId(id);
        existing.setName("Old");
        existing.setDescription("Old Desc");

        when(panelRepo.findById(id)).thenReturn(Optional.of(existing));
        when(panelRepo.save(existing)).thenReturn(existing);
        when(mappingRepo.findByPanel_PanelId(id)).thenReturn(List.of());

        TestPanelRequest upd = new TestPanelRequest();
        upd.setName("New");
        upd.setDescription("New Desc");

        TestPanelResponse out = service.updatePanel(id, upd);
        assertEquals("New", out.getName());
        assertEquals("New Desc", out.getDescription());
    }
}
