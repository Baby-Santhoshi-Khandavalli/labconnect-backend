package com.labconnect.services.testCatalog;
import com.labconnect.DTOResponse.testCatalog.PanelMappingResponse;
import com.labconnect.mapper.testCatalog.PanelMappingMapper;
import com.labconnect.models.testCatalog.PanelMapping;
import com.labconnect.models.testCatalog.Test;
import com.labconnect.models.testCatalog.TestPanel;
import com.labconnect.repository.testCatalog.PanelMappingRepository;
import com.labconnect.repository.testCatalog.TestPanelRepository;
import com.labconnect.repository.testCatalog.TestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PanelMappingService {

    private final PanelMappingRepository mappingRepository;
    private final TestPanelRepository panelRepository;
    private final TestRepository testRepository;
    private final PanelMappingMapper mapper; // <-- inject mapper

    public PanelMappingService(PanelMappingRepository mappingRepository,
                               TestPanelRepository panelRepository,
                               TestRepository testRepository,
                               PanelMappingMapper mapper) {   // <-- add to constructor
        this.mappingRepository = mappingRepository;
        this.panelRepository = panelRepository;
        this.testRepository = testRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<PanelMappingResponse> getMappingsByPanel(Long panelId) {
        return mappingRepository.findByPanel_PanelId(panelId).stream()
                .map(mapper::toResponse)          // <-- instance method, not static
                .toList();                         // If you're on Java <16, use .collect(Collectors.toList())
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
        return mapper.toResponse(saved);           // <-- instance method
    }

    @Transactional
    public void removeTestFromPanel(Long mappingId) {
        mappingRepository.deleteById(mappingId);
    }
}