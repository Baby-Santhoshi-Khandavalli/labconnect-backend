package com.labconnect.controller.workFlow;


import com.labconnect.DTORequest.workFlow.TestWorkFlowRequestDTO;
import com.labconnect.DTOResponse.workFlow.TestWorkFlowResponseDTO;
import com.labconnect.mapper.workflow.TestWorkFlowMapper;
import com.labconnect.models.workFlow.TestWorkFlow;
import com.labconnect.services.workFlow.TestWorkFlowService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestWorkFlowController.class)
class WorkFlowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TestWorkFlowService service;

    @MockitoBean
    private TestWorkFlowMapper mapper;

    @Test
    void testGetAllWorkflows() throws Exception {
        TestWorkFlow workflow = new TestWorkFlow();
        TestWorkFlowResponseDTO dto = new TestWorkFlowResponseDTO();

        Mockito.when(service.getAllWorkflows()).thenReturn(List.of(workflow));
        Mockito.when(mapper.toResponseDTO(workflow)).thenReturn(dto);

        mockMvc.perform(get("/api/workflows"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateWorkflow() throws Exception {
        TestWorkFlowRequestDTO requestDTO = new TestWorkFlowRequestDTO();
        TestWorkFlow workflow = new TestWorkFlow();
        TestWorkFlowResponseDTO responseDTO = new TestWorkFlowResponseDTO();

        Mockito.when(mapper.toEntity(any())).thenReturn(workflow);
        Mockito.when(service.createWorkflow(workflow)).thenReturn(workflow);
        Mockito.when(mapper.toResponseDTO(workflow)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/workflows")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"field\":\"value\"}"))
                .andExpect(status().isOk());
    }
}
