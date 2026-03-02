package com.labconnect.service.Identity;
import com.labconnect.DTORequest.Identity.UserRequestDTO;
import com.labconnect.DTOResponse.Identity.UserResponseDTO;
import com.labconnect.mapper.Identity.IdentityMapper;
import com.labconnect.models.Identity.User;
import com.labconnect.repository.Identity.AuditLogRepository;
import com.labconnect.repository.Identity.UserRepository;
import com.labconnect.services.Identity.IdentityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;

public class IdentityServiceTest {
    @Mock private UserRepository userRepository;
    @Mock private AuditLogRepository auditLogRepository;
    @Mock private IdentityMapper mapper;

    @InjectMocks
    private IdentityService identityService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser_Logic(){
        UserRequestDTO userRequestDTO=new UserRequestDTO();
        userRequestDTO.setEmail("new@lab.com");

        User userEntity=new User();
        userEntity.setEmail("new@lab.com");

        UserResponseDTO responseDTO=new UserResponseDTO();
        responseDTO.setEmail("new@lab.com");

        Mockito.when(mapper.mapToUserEntity(any())).thenReturn(userEntity);
        Mockito.when(userRepository.save(any())).thenReturn(userEntity);
        Mockito.when(mapper.mapToUserResponseDTO(any())).thenReturn(responseDTO);

        UserResponseDTO result=identityService.createUser(userRequestDTO);

        Assertions.assertEquals("new@lab.com",result.getEmail());
        Mockito.verify(userRepository, Mockito.times(1)).save(any());
        Mockito.verify(auditLogRepository, Mockito.times(1)).save(any());
    }
}
