//package com.labconnect.services;
//
//
//import com.labconnect.DTORequest.UserRequestDTO;
//import com.labconnect.DTOResponse.AuditLogDTO;
//import com.labconnect.DTOResponse.UserResponseDTO;
//import com.labconnect.Exception.ResourceNotFoundException;
//import com.labconnect.mapper.IdentityMapper;
//import com.labconnect.models.AuditLog;
//import com.labconnect.models.User;
//import com.labconnect.repository.AuditLogRepository;
//import com.labconnect.repository.UserRepository;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class IdentityService {
//    private final UserRepository userRepository;
//    private final AuditLogRepository auditLogRepository;
//    private final IdentityMapper mapper;
//    private final PasswordEncoder passwordEncoder;
//    //private final BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
//
////    @Autowired
////    private PasswordEncoder passwordEncoder;
//
//    public IdentityService(UserRepository userRepository, AuditLogRepository auditLogRepository,IdentityMapper mapper, PasswordEncoder passwordEncoder){
//        this.userRepository=userRepository;
//        this.auditLogRepository=auditLogRepository;
//        this.mapper=mapper;
//        this.passwordEncoder=passwordEncoder;
//    }
//
//
//
////    public User createUser(User user){
////        User savedUser=userRepository.save(user);
////        logAction(savedUser.getUserId(),"CREATE_USER","User created", "Name="+savedUser.getName());
////        return savedUser;
////    }
//
//    @Transactional
//    public UserResponseDTO createUser(UserRequestDTO request){
////        User user=new User();
////        user.setName(request.getName());
////        user.setRole(request.getRole());
////        user.setEmail(request.getEmail());
////        user.setPhone(request.getPhone());
//        User user=mapper.mapToUserEntity(request);
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//
//        User saved=userRepository.save(user);
//        logAction(saved,"CREATE_USER","User Account Created","Email="+saved.getEmail());
//        //return mapToUserResponseDTO(saved);
//        return mapper.mapToUserResponseDTO(saved);
//    }
//
////    public List<User> getAllUsers(){
////        return userRepository.findAll();
////    }
//
////    public List<UserResponseDTO> getAllUsers(){
////        return userRepository.findAll().stream().map(this::mapToUserResponseDTO).collect(Collectors.toList());
////    }
//
//    public List<UserResponseDTO> getAllUsers(){
//        return userRepository.findAll().stream().map(mapper::mapToUserResponseDTO).collect(Collectors.toList());
//    }
//
////    public User getUserByEmail(String email){
////        return userRepository.findByEmail(email);
////    }
//
//    public UserResponseDTO getUserByEmail(String email){
//        User user=userRepository.findByEmail(email);
//        if(user==null){
//            throw new ResourceNotFoundException("User not found with email: "+email);
//        }
//        //return mapToUserResponseDTO(user);
//        return mapper.mapToUserResponseDTO(user);
//    }
//
//    @Transactional
//    public UserResponseDTO updateUser(Long id, UserRequestDTO request){
//        User user=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found with ID: "+id));
//        user.setName(request.getName());
//        user.setRole(request.getRole());
//        user.setPhone(request.getPhone());
//        User updated=userRepository.save(user);
//        logAction(updated,"UPDATE","USER","Updated profile");
//        return mapper.mapToUserResponseDTO(updated);
//    }
//
//    public void deleteUser(Long id){
//        if(!userRepository.existsById(id)) throw new ResourceNotFoundException("ID not found");
//        userRepository.deleteById(id);
//    }
//
//    public List<AuditLogDTO> getAuditLogsForUser(Long userId){
//        if(!userRepository.existsById(userId)){
//            throw new ResourceNotFoundException("User ID "+userId+" does not exist.");
//        }
//        //return auditLogRepository.findByUser_UserId(userId).stream().map(this::mapToAuditLogDTO).collect(Collectors.toList());
//        return auditLogRepository.findByUser_UserId(userId).stream().map(mapper::mapToAuditLogDTO).collect(Collectors.toList());
//    }
////===========Helper Methods==============================
//    private void logAction(User user,String action,String resource, String metadata){
//        AuditLog log=new AuditLog();
//        log.setUser(user);
//        log.setAction(action);
//        log.setResource(resource);
//        log.setTimestamp(LocalDateTime.now());
//        log.setMetadata(metadata);
//        auditLogRepository.save(log);
//    }
//
////    private UserResponseDTO mapToUserResponseDTO(User user){
////        UserResponseDTO resp=new UserResponseDTO();
////        resp.setUserId(user.getUserId());
////        resp.setName(user.getName());
////        resp.setEmail(user.getEmail());
////        resp.setPhone(user.getPhone());
////        resp.setRole(user.getRole());
////        return resp;
////    }
////
////    private AuditLogDTO mapToAuditLogDTO(AuditLog log){
////        AuditLogDTO audit=new AuditLogDTO();
////        audit.setAuditId(log.getAuditId());
////        audit.setAction(log.getAction());
////        audit.setResource(log.getResource());
////        audit.setTimestamp(log.getTimestamp());
////        audit.setMetadata(log.getMetadata());
////        return audit;
////    }
//}
