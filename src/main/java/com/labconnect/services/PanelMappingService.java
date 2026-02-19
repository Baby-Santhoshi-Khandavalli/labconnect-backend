package com.labconnect.services;

import com.labconnect.DTOResponse.PanelMappingResponse;
import com.labconnect.mapper.PanelMappingMapper;
import com.labconnect.models.PanelMapping;
import com.labconnect.models.Test;
import com.labconnect.models.TestPanel;
import com.labconnect.repository.PanelMappingRepository;
import com.labconnect.repository.TestPanelRepository;
import com.labconnect.repository.TestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PanelMappingService {
    private final PanelMappingRepository mappingRepository;
    private final TestPanelRepository panelRepository;
    private final TestRepository testRepository;

    public PanelMappingService(PanelMappingRepository mappingRepository,
                               TestPanelRepository panelRepository,
                               TestRepository testRepository) {
        this.mappingRepository = mappingRepository;
        this.panelRepository = panelRepository;
        this.testRepository = testRepository;
    }

    @Transactional(readOnly = true)
    public List<PanelMappingResponse> getMappingsByPanel(Long panelId) {
        // Efficient: use a repository method instead of fetching all and filtering
        return mappingRepository.findByPanel_PanelId(panelId).stream()
                .map(PanelMappingMapper::toResponse)
                .toList();
    }

    @Transactional
    public PanelMappingResponse addTestToPanel(Long panelId, Long testId) {
        if (mappingRepository.existsByPanel_PanelIdAndTest_TestId(panelId, testId)) {
            throw new RuntimeException("Test already exists in panel");
        }
        TestPanel panel = panelRepository.findById(panelId)
                .orElseThrow(() -> new RuntimeException("Panel not found"));
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test not found"));

        PanelMapping mapping = new PanelMapping();
        mapping.setPanel(panel);
        mapping.setTest(test);

        PanelMapping saved = mappingRepository.save(mapping);
        return PanelMappingMapper.toResponse(saved);
    }

    @Transactional
    public void removeTestFromPanel(Long mappingId) {
        mappingRepository.deleteById(mappingId);
    }
}
