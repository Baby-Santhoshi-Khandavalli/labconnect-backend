//package com.labconnect.controller;
//
//import com.labconnect.controller.TestWorkflowController;
//import com.labconnect.DTORequest.TestWorkflowRequestDTO;
//import com.labconnect.DTOResponse.TestWorkflowResponseDTO;
//import com.labconnect.mapper.TestWorkflowMapper;
//import com.labconnect.models.TestWorkflow;
//import com.labconnect.services.TestWorkflowService;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(TestWorkflowController.class)
//class WorkflowControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockitoBean
//    private TestWorkflowService service;
//
//    @MockitoBean
//    private TestWorkflowMapper mapper;
//
//    @Test
//    void testGetAllWorkflows() throws Exception {
//        TestWorkflow workflow = new TestWorkflow();
//        TestWorkflowResponseDTO dto = new TestWorkflowResponseDTO();
//
//        Mockito.when(service.getAllWorkflows()).thenReturn(List.of(workflow));
//        Mockito.when(mapper.toResponseDTO(workflow)).thenReturn(dto);
//
//        mockMvc.perform(get("/api/workflows"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void testCreateWorkflow() throws Exception {
//        TestWorkflowRequestDTO requestDTO = new TestWorkflowRequestDTO();
//        TestWorkflow workflow = new TestWorkflow();
//        TestWorkflowResponseDTO responseDTO = new TestWorkflowResponseDTO();
//
//        Mockito.when(mapper.toEntity(any())).thenReturn(workflow);
//        Mockito.when(service.createWorkflow(workflow)).thenReturn(workflow);
//        Mockito.when(mapper.toResponseDTO(workflow)).thenReturn(responseDTO);
//
//        mockMvc.perform(post("/api/workflows")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"field\":\"value\"}"))
//                .andExpect(status().isOk());
//    }
//}
