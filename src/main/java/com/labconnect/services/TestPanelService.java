package com.labconnect.services;

import com.labconnect.DTORequest.TestPanelRequest;
import com.labconnect.DTOResponse.TestPanelResponse;
import com.labconnect.models.PanelMapping;
import com.labconnect.models.Test;
import com.labconnect.models.TestPanel;
import com.labconnect.repository.PanelMappingRepository;
import com.labconnect.repository.TestPanelRepository;
import com.labconnect.repository.TestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.labconnect.mapper.TestPanelMapper.toResponse;

@Service
public class TestPanelService {

    private final TestPanelRepository repository;
    private final TestRepository testRepository;
    private final PanelMappingRepository panelMappingRepository;

    public TestPanelService(TestPanelRepository repository,
                            TestRepository testRepository,
                            PanelMappingRepository panelMappingRepository) {
        this.repository = repository;
        this.testRepository = testRepository;
        this.panelMappingRepository = panelMappingRepository;
    }

    @Transactional
    public TestPanelResponse createPanel(TestPanelRequest request) {
        // 1) Create and save panel
        TestPanel panel = new TestPanel();
        panel.setName(request.getName());
        panel.setDescription(request.getDescription());
        panel = repository.save(panel);

        List<PanelMapping> mappings = new ArrayList<>();
        if (request.getTestIds() != null) {
            for (Long testId : request.getTestIds()) {
                Test test = testRepository.findById(testId)
                        .orElseThrow(() -> new RuntimeException("Test not found: " + testId));
                PanelMapping mapping = new PanelMapping();
                mapping.setPanel(panel);
                mapping.setTest(test);
                mappings.add(mapping);
            }
        }

        // 3) Persist mappings and attach
        if (!mappings.isEmpty()) {
            panelMappingRepository.saveAll(mappings);
        }
        panel.setPanelMappings(mappings);

        // 4) Return DTO
        return toResponse(panel, mappings);
    }

    @Transactional
    public TestPanelResponse updatePanel(Long id, TestPanelRequest updated) {
        TestPanel existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Panel not found"));

        if (updated.getName() != null) {
            existing.setName(updated.getName());
        }
        if (updated.getDescription() != null) {
            existing.setDescription(updated.getDescription());
        }
        repository.save(existing);

        // If caller passed testIds, we could (optionally) update mappings here.
        // Current behavior: name/description only. Mapping changes via dedicated endpoints.

        // Reload mappings for response
        List<PanelMapping> mappings = panelMappingRepository.findByPanel_PanelId(id);
        return toResponse(existing, mappings);
    }

    @Transactional(readOnly = true)
    public TestPanelResponse getPanelById(Long id) {
        TestPanel panel = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Panel not found"));
        List<PanelMapping> mappings = panelMappingRepository.findByPanel_PanelId(id);
        return toResponse(panel, mappings);
    }

    @Transactional(readOnly = true)
    public List<TestPanelResponse> getAllPanels() {
        List<TestPanel> panels = repository.findAll();
        // For each panel, fetch mappings (simple & clear; optimize with a join fetch if needed)
        return panels.stream()
                .map(p -> toResponse(p, panelMappingRepository.findByPanel_PanelId(p.getPanelId())))
                .toList();
    }

    /** Helper to rebuild a fresh DTO for a panel */
    @Transactional(readOnly = true)
    public TestPanelResponse getPanelResponse(Long panelId) {
        return getPanelById(panelId);
    }
}

