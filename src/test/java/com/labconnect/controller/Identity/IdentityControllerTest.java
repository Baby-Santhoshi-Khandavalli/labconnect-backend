package com.labconnect.controller.Identity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labconnect.DTORequest.Identity.UserRequestDTO;
import com.labconnect.DTOResponse.Identity.UserResponseDTO;
import com.labconnect.repository.Identity.UserRepository;
import com.labconnect.security.JwtService;
import com.labconnect.security.MyUserDetailsService;
import com.labconnect.services.Identity.IdentityService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IdentityController.class)
public class IdentityControllerTest {
    @Autowired
    private MockMvc mockMvc;

    //@Autowired
    private ObjectMapper objectMapper=new ObjectMapper();

    @MockitoBean
    private IdentityService identityService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private MyUserDetailsService myUserDetailsService;

    @MockitoBean
    private UserRepository userRepository;

//    @Test
//    @WithMockUser(username = "admin@lab.com", roles = {"Admin"})
//    public void testGetAllUsers_Authorized() throws Exception{
//        //mockMvc.perform(get("/api/identity/users").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
//        mockMvc.perform(get("/api/identity/users").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
//    }

    @Test
    @WithMockUser(roles = "Admin")
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/api/identity/users"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testCreateUser() throws Exception{
        UserRequestDTO request=new UserRequestDTO();
        request.setEmail("test@test.com");
        request.setPassword("pass");

        Mockito.when(identityService.createUser(Mockito.any())).thenReturn(new UserResponseDTO());

        mockMvc.perform(post("/api/identity/users").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request))).andExpect(status().isCreated());
    }
}
