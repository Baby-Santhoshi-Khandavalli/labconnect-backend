package com.labconnect.services.Identity;



import com.labconnect.DTORequest.Identity.UserRequestDTO;
import com.labconnect.DTOResponse.Identity.AuditLogDTO;
import com.labconnect.DTOResponse.Identity.UserResponseDTO;
import com.labconnect.Exception.Identity.ResourceNotFoundException;
import com.labconnect.mapper.Identity.IdentityMapper;
import com.labconnect.models.Identity.AuditLog;
import com.labconnect.models.Identity.User;
import com.labconnect.repository.Identity.AuditLogRepository;
import com.labconnect.repository.Identity.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IdentityService {
    private final UserRepository userRepository;
    private final AuditLogRepository auditLogRepository;
    private final IdentityMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public IdentityService(UserRepository userRepository, AuditLogRepository auditLogRepository,IdentityMapper mapper, PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.auditLogRepository=auditLogRepository;
        this.mapper=mapper;
        this.passwordEncoder=passwordEncoder;
    }

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO request){
        User user=mapper.mapToUserEntity(request);
        String securePassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(securePassword);

        User saved=userRepository.save(user);
        logAction(saved,"CREATE_USER","User Account Created","Email="+saved.getEmail());
        return mapper.mapToUserResponseDTO(saved);
    }

    public List<UserResponseDTO> getAllUsers(){
        return userRepository.findAll().stream().map(mapper::mapToUserResponseDTO).collect(Collectors.toList());
    }

    public UserResponseDTO getUserByEmail(String email){
        User user=userRepository.findByEmail(email);
        if(user==null){
            throw new ResourceNotFoundException("User not found with email: "+email);
        }
        return mapper.mapToUserResponseDTO(user);
    }

    @Transactional
    public UserResponseDTO updateUser(Long id, UserRequestDTO request){
        User user=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found with ID: "+id));
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        user.setPhone(request.getPhone());
        User updated=userRepository.saveAndFlush(user);
        logAction(updated,"UPDATE","USER","Updated profile");
        return mapper.mapToUserResponseDTO(updated);
    }

    public void deleteUser(Long id){
        if(!userRepository.existsById(id)) throw new ResourceNotFoundException("ID not found");
        userRepository.deleteById(id);
    }

    public List<AuditLogDTO> getAuditLogsForUser(Long userId){
        if(!userRepository.existsById(userId)){
            throw new ResourceNotFoundException("User ID "+userId+" does not exist.");
        }
        return auditLogRepository.findByUserUserId(userId).stream().map(mapper::mapToAuditLogDTO).collect(Collectors.toList());
    }

    private void logAction(User user,String action,String resource, String metadata){
        AuditLog log=new AuditLog();
        log.setUser(user);
        log.setAction(action);
        log.setResource(resource);
        log.setTimestamp(LocalDateTime.now());
        log.setMetadata(metadata);
        auditLogRepository.save(log);
    }
}
