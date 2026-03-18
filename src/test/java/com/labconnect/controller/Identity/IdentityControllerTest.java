package com.labconnect.controller.Identity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labconnect.DTORequest.Identity.UserRequestDTO;
import com.labconnect.DTORequest.LoginRequest;

import com.labconnect.DTOResponse.Identity.UserResponseDTO;
import com.labconnect.Exception.Identity.ResourceNotFoundException;
import com.labconnect.repository.Identity.UserRepository;
import com.labconnect.security.JwtService;
import com.labconnect.security.MyUserDetailsService;
import com.labconnect.services.Identity.IdentityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

//import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

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

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private PasswordEncoder passwordEncoder;


    @Test
    @WithMockUser(roles = "Admin")
    @DisplayName("GET /users - Success")
    public void testGetAllUsers_Success() throws Exception {
        List<UserResponseDTO> users = Collections.singletonList(new UserResponseDTO());
        when(identityService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/identity/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @WithMockUser
    @DisplayName("POST/ login - Success")
    public void testLogin_Success() throws Exception{
        LoginRequest loginRequest=new LoginRequest();
        loginRequest.setEmail("admin@lab.com");
        loginRequest.setPassword("password123");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken("admin@lab.com", null));
        when(jwtService.generateToken("admin@lab.com")).thenReturn("mocked-jwt-token");

        mockMvc.perform(post("/api/identity/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("mocked-jwt-token"));
    }

    @Test
    @WithMockUser
    @DisplayName("GET /users/{email} - Success")
    public void testGetUserByEmail_Success() throws Exception {
        UserResponseDTO response = new UserResponseDTO();
        response.setEmail("test@lab.com");
        when(identityService.getUserByEmail("test@lab.com")).thenReturn(response);

        mockMvc.perform(get("/api/identity/users/test@lab.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@lab.com"));
    }

    @Test
    @WithMockUser
    @DisplayName("PUT /users/{id} - Success")
    public void testUpdateUser_Success() throws Exception {
        UserRequestDTO request = new UserRequestDTO();
        request.setName("Updated Name");

        when(identityService.updateUser(eq(1L), any(UserRequestDTO.class))).thenReturn(new UserResponseDTO());

        mockMvc.perform(put("/api/identity/users/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("DELETE /users/{id} - Success")
    public void testDeleteUser_Success() throws Exception {
        doNothing().when(identityService).deleteUser(1L);

        mockMvc.perform(delete("/api/identity/users/1").with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    @DisplayName("POST /login - Failure (Invalid Credentials)")
    public void testLogin_Failure() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("wrong@lab.com");
        loginRequest.setPassword("wrongpass");

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Invalid email or password"));

        mockMvc.perform(post("/api/identity/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid email or password"));
    }

    @Test
    @WithMockUser
    @DisplayName("GET /users/{email} - Not Found")
    public void testGetUserByEmail_NotFound() throws Exception {
        when(identityService.getUserByEmail("missing@lab.com"))
                .thenThrow(new ResourceNotFoundException("User not found"));

        mockMvc.perform(get("/api/identity/users/missing@lab.com"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /users - Unauthorized (No Token)")
    public void testGetAllUsers_Unauthorized() throws Exception {
        // No @WithMockUser here
        mockMvc.perform(get("/api/identity/users"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    @DisplayName("GET /audit/{userId} - User Not Found")
    public void testGetAuditLogs_NotFound() throws Exception {
        when(identityService.getAuditLogsForUser(99L))
                .thenThrow(new ResourceNotFoundException("User ID 99 does not exist."));

        mockMvc.perform(get("/api/identity/audit/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    @DisplayName("POST /register - Malformed JSON")
    public void testCreateUser_InvalidJson() throws Exception {
        // Ensure this URL matches your @PostMapping in IdentityController
        // If your controller uses @PostMapping("/register"), keep it as /register
        mockMvc.perform(post("/api/identity/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"invalid\": \"json\" ")) // Missing closing brace
                .andExpect(status().isBadRequest());
    }

}
