package com.labconnect.controller.workFlow;

import com.labconnect.DTORequest.workFlow.TestWorkFlowRequestDTO;
import com.labconnect.DTOResponse.workFlow.TestWorkFlowResponseDTO;
import com.labconnect.Enum.WorkflowStatus;
import com.labconnect.mapper.workflow.TestWorkFlowMapper;
import com.labconnect.models.workFlow.TestWorkFlow;
import com.labconnect.services.workFlow.TestWorkFlowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class WorkFlowControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TestWorkFlowService service;

    @Mock
    private TestWorkFlowMapper mapper;

    @InjectMocks
    private TestWorkFlowController controller;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testGetAllWorkflows() throws Exception {
        TestWorkFlow workflow = new TestWorkFlow();
        TestWorkFlowResponseDTO responseDTO = new TestWorkFlowResponseDTO();
        responseDTO.setWorkflowId(1L);
        responseDTO.setOrderId(100L);
        responseDTO.setTestId(200L);
        responseDTO.setStatus(WorkflowStatus.IN_PROGRESS);

        when(service.getAllWorkflows()).thenReturn(List.of(workflow));
        when(mapper.toResponseDTO(workflow)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/test-workflows"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].workflowId").value(1L))
                .andExpect(jsonPath("$[0].orderId").value(100L))
                .andExpect(jsonPath("$[0].testId").value(200L))
                .andExpect(jsonPath("$[0].status").value("IN_PROGRESS"));

        verify(service, times(1)).getAllWorkflows();
        verify(mapper, times(1)).toResponseDTO(workflow);
    }

    @Test
    void testCreateWorkflow() throws Exception {
        TestWorkFlow workflow = new TestWorkFlow();
        TestWorkFlowResponseDTO responseDTO = new TestWorkFlowResponseDTO();
        responseDTO.setWorkflowId(10L);
        responseDTO.setStatus(WorkflowStatus.PENDING); // use a valid enum constant

        when(mapper.toEntity(any(TestWorkFlowRequestDTO.class))).thenReturn(workflow);
        when(service.createWorkflow(workflow)).thenReturn(workflow);
        when(mapper.toResponseDTO(workflow)).thenReturn(responseDTO);

        String validJson = """
        {
          "orderId": 1,
          "testId": 2,
          "status": "PENDING",
          "assigneeId": 3
        }
        """;

        mockMvc.perform(post("/api/test-workflows")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.workflowId").value(10L))
                .andExpect(jsonPath("$.status").value("PENDING"));

        verify(service, times(1)).createWorkflow(workflow);
        verify(mapper, times(1)).toEntity(any(TestWorkFlowRequestDTO.class));
        verify(mapper, times(1)).toResponseDTO(workflow);
    }

    @Test
    void testUpdateWorkflowStatus() throws Exception {
        TestWorkFlow workflow = new TestWorkFlow();
        TestWorkFlowResponseDTO responseDTO = new TestWorkFlowResponseDTO();
        responseDTO.setWorkflowId(5L);
        responseDTO.setStatus(WorkflowStatus.COMPLETED);
        responseDTO.setEndTime(LocalDateTime.now());

        when(service.updateWorkflowStatus(5L, "COMPLETED")).thenReturn(workflow);
        when(mapper.toResponseDTO(workflow)).thenReturn(responseDTO);

        mockMvc.perform(put("/api/test-workflows/5/status")
                        .param("status", "COMPLETED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.workflowId").value(5L))
                .andExpect(jsonPath("$.status").value("COMPLETED"));

        verify(service, times(1)).updateWorkflowStatus(5L, "COMPLETED");
        verify(mapper, times(1)).toResponseDTO(workflow);
    }
}
