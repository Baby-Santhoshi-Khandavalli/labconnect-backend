package com.labconnect.service.Identity;


import com.labconnect.DTORequest.Identity.UserRequestDTO;
import com.labconnect.DTOResponse.Identity.AuditLogDTO;
import com.labconnect.DTOResponse.Identity.UserResponseDTO;
import com.labconnect.Exception.Identity.ResourceNotFoundException;
import com.labconnect.mapper.Identity.IdentityMapper;
import com.labconnect.models.Identity.AuditLog;
import com.labconnect.models.Identity.User;
import com.labconnect.repository.Identity.AuditLogRepository;
import com.labconnect.repository.Identity.UserRepository;
import com.labconnect.services.Identity.IdentityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
public class IdentityServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private AuditLogRepository auditLogRepository;
    @Mock private IdentityMapper mapper;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks
    private IdentityService identityService;

    // --- CREATE USER TESTS ---

    @Test
    @DisplayName("Create User: Success")
    public void testCreateUser_Success() {
        UserRequestDTO request = new UserRequestDTO();
        request.setEmail("test@lab.com");
        request.setPassword("plainText");

        User user = new User();
        user.setEmail("test@lab.com");

        when(mapper.mapToUserEntity(any())).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(userRepository.save(any())).thenReturn(user);
        when(mapper.mapToUserResponseDTO(any())).thenReturn(new UserResponseDTO());

        identityService.createUser(request);

        verify(userRepository, times(1)).save(any());
        verify(auditLogRepository, times(1)).save(any());
        verify(passwordEncoder).encode("plainText");
    }

    // --- GET USER TESTS ---

    @Test
    @DisplayName("Get User By Email: Success")
    public void testGetUserByEmail_Success() {
        User user = new User();
        user.setEmail("find@me.com");

        when(userRepository.findByEmail("find@me.com")).thenReturn(user);
        when(mapper.mapToUserResponseDTO(user)).thenReturn(new UserResponseDTO());

        UserResponseDTO result = identityService.getUserByEmail("find@me.com");

        assertNotNull(result);
    }

    @Test
    @DisplayName("Get User By Email: Not Found (Negative)")
    public void testGetUserByEmail_NotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            identityService.getUserByEmail("missing@lab.com");
        });
    }

    // --- UPDATE USER TESTS ---

    @Test
    @DisplayName("Update User: Success")
    public void testUpdateUser_Success() {
        Long id = 1L;
        UserRequestDTO request = new UserRequestDTO();
        request.setName("New Name");

        User existingUser = new User();
        existingUser.setUserId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(userRepository.saveAndFlush(any())).thenReturn(existingUser);
        when(mapper.mapToUserResponseDTO(any())).thenReturn(new UserResponseDTO());

        UserResponseDTO result = identityService.updateUser(id, request);

        assertNotNull(result);
        verify(userRepository).saveAndFlush(any());
    }

    @Test
    @DisplayName("Update User: ID Not Found (Negative)")
    public void testUpdateUser_NotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            identityService.updateUser(99L, new UserRequestDTO());
        });
    }

    // --- DELETE USER TESTS ---

    @Test
    @DisplayName("Delete User: Success")
    public void testDeleteUser_Success() {
        when(userRepository.existsById(1L)).thenReturn(true);

        identityService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Delete User: ID Not Found (Negative)")
    public void testDeleteUser_NotFound() {
        when(userRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> identityService.deleteUser(1L));
    }

    // --- AUDIT LOG TESTS ---

    @Test
    @DisplayName("Get Audit Logs: Success")
    public void testGetAuditLogs_Success() {
        when(userRepository.existsById(1L)).thenReturn(true);
        when(auditLogRepository.findByUserUserId(1L)).thenReturn(Collections.singletonList(new AuditLog()));

        List<AuditLogDTO> logs = identityService.getAuditLogsForUser(1L);

        assertFalse(logs.isEmpty());
    }
}