package com.labconnect.service;
import com.labconnect.DTORequest.TestPanelRequest;
import com.labconnect.DTOResponse.TestPanelResponse;
import com.labconnect.mapper.TestPanelMapper;
import com.labconnect.models.PanelMapping;
import com.labconnect.models.Test;
import com.labconnect.models.TestPanel;
import com.labconnect.repository.PanelMappingRepository;
import com.labconnect.repository.TestPanelRepository;
import com.labconnect.repository.TestRepository;
import com.labconnect.services.TestPanelService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestPanelServiceTest {

    @Mock private TestPanelRepository panelRepo;
    @Mock private TestRepository testRepo;
    @Mock private PanelMappingRepository mappingRepo;
    @Mock private TestPanelMapper mapper;

    @InjectMocks
    private TestPanelService service;

      @org.junit.jupiter.api.Test

    @DisplayName("createPanel: with testIds -> creates mappings and returns DTO")
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

        // saveAll should return the same list back (simulate persistence)
        when(mappingRepo.saveAll(anyList())).thenAnswer(inv -> inv.getArgument(0));

        // The service will call mapper.toResponse(panel, mappings). Stub it to produce a DTO.
        when(mapper.toResponse(eq(savedPanel), anyList()))
                .thenAnswer(inv -> buildResponseFrom(inv.getArgument(0), inv.getArgument(1)));

        // Act
        TestPanelResponse out = service.createPanel(req);

        // Assert
        assertNotNull(out);
        assertEquals(1L, out.getPanelId());
        assertEquals("Renal Panel", out.getName());
        assertEquals("Kidney tests", out.getDescription());
        assertTrue(out.getTestNames().contains("Urea"));
        assertTrue(out.getTestNames().contains("Creatinine"));

        verify(panelRepo).save(any(TestPanel.class));
        verify(mappingRepo).saveAll(anyList());
        verify(mapper).toResponse(eq(savedPanel), anyList());
    }

    @org.junit.jupiter.api.Test

    @DisplayName("updatePanel: updates name/description and returns DTO")
    void updatePanel_updatesNameAndDescription() {
        // Arrange
        Long id = 77L;
        TestPanel existing = new TestPanel();
        existing.setPanelId(id);
        existing.setName("Old");
        existing.setDescription("Old Desc");

        when(panelRepo.findById(id)).thenReturn(Optional.of(existing));

        when(panelRepo.save(any(TestPanel.class))).thenAnswer(inv -> inv.getArgument(0));

        List<PanelMapping> existingMappings = List.of(); // no mappings
        when(mappingRepo.findByPanel_PanelId(id)).thenReturn(existingMappings);

        when(mapper.toResponse(eq(existing), eq(existingMappings)))
                .thenAnswer(inv -> buildResponseFrom(inv.getArgument(0), inv.getArgument(1)));

        TestPanelRequest upd = new TestPanelRequest();
        upd.setName("New");
        upd.setDescription("New Desc");
        TestPanelResponse out = service.updatePanel(id, upd);
        assertNotNull(out);
        assertEquals(id, out.getPanelId());
        assertEquals("New", out.getName());
        assertEquals("New Desc", out.getDescription());
        assertNotNull(out.getTestNames());
        assertTrue(out.getTestNames().isEmpty());

        verify(panelRepo).findById(id);
        verify(panelRepo).save(any(TestPanel.class));
        verify(mappingRepo).findByPanel_PanelId(id);
        verify(mapper).toResponse(eq(existing), eq(existingMappings));
    }

    private static TestPanelResponse buildResponseFrom(TestPanel panel, List<PanelMapping> mappings) {
        TestPanelResponse resp = new TestPanelResponse();
        if (panel != null) {
            resp.setPanelId(panel.getPanelId());
            resp.setName(panel.getName());
            resp.setDescription(panel.getDescription());
        }
        List<String> names = new ArrayList<>();
        if (mappings != null) {
            for (PanelMapping m : mappings) {
                if (m != null && m.getTest() != null && m.getTest().getName() != null) {
                    names.add(m.getTest().getName());
                }
            }
        }
        resp.setTestNames(names);
        return resp;
    }
}