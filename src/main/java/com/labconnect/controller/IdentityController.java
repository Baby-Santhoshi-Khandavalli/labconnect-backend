package com.labconnect.controller;

import com.labconnect.DTORequest.LoginRequest;
import com.labconnect.DTORequest.UserRequestDTO;
import com.labconnect.DTOResponse.AuditLogDTO;
import com.labconnect.DTOResponse.UserResponseDTO;
import com.labconnect.Exception.ResourceNotFoundException;
import com.labconnect.models.AuditLog;
import com.labconnect.models.User;
import com.labconnect.security.JwtService;
import com.labconnect.services.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/identity")
public class IdentityController {
    private final IdentityService identityService;

    public IdentityController(IdentityService identityService){
        this.identityService=identityService;
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
//        String token= jwtService.generateToken(loginRequest.getEmail());
//        return ResponseEntity.ok(token);
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // This is the "Magic" line that checks the email and password in the DB
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            // If we reach here, the password was correct!
            String token = jwtService.generateToken(loginRequest.getEmail());
            return ResponseEntity.ok(token);

        } catch (Exception e) {
            e.printStackTrace();
            // If password is wrong or user doesn't exist, return 401
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

//    @GetMapping("/users")
//    public List<User> getUsers(){
//        return identityService.getAllUsers();
//    }

//    @GetMapping("/users")
//    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
//        try{
//            List<UserResponseDTO> users=identityService.getAllUsers();
//            return new ResponseEntity<>(users,HttpStatus.OK);
//        }catch (Exception e){
//            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        return ResponseEntity.ok(identityService.getAllUsers());
    }

//    @PostMapping("/users")
//    public User createUser(@RequestBody User user){
//        return identityService.createUser(user);
//    }

//    @PostMapping("/users")
//    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO){
//        try{
//            UserResponseDTO createdUser=identityService.createUser(userRequestDTO);
//            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
//        }catch (Exception e){
//            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
//        }
//    }

    @PostMapping("/users")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO){
        return new ResponseEntity<>(identityService.createUser(userRequestDTO),HttpStatus.CREATED);
    }

//    @GetMapping("/users/{email}")
//    public User getUserByEmail(@PathVariable String email){
//        return identityService.getUserByEmail(email);
//    }

//    @GetMapping("/users/{email}")
//    public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email){
//        try{
//            UserResponseDTO user=identityService.getUserByEmail(email);
//            return new ResponseEntity<>(user,HttpStatus.OK);
//        }catch (ResourceNotFoundException e){
//            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
//        }
//    }

    @GetMapping("/users/{email}")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email){
        return ResponseEntity.ok(identityService.getUserByEmail(email));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO){
        return ResponseEntity.ok(identityService.updateUser(id,userRequestDTO));
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAuthority('Admin')") // Only Admins can delete users
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        identityService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/audit/{userId}")
//    public List<AuditLog> getAuditLogsForUser(@PathVariable Long userId){
//        return identityService.getAuditLogsForUser(userId);
//    }

//    @GetMapping("/audit/{userId}")
//    public ResponseEntity<List<AuditLogDTO>> getAuditLogsForUser(@PathVariable Long userId){
//        try{
//            List<AuditLogDTO> logs=identityService.getAuditLogsForUser(userId);
//            return new ResponseEntity<>(logs,HttpStatus.OK);
//        }catch (ResourceNotFoundException e){
//            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
//        }
//    }

    @GetMapping("/audit/{userId}")
    public ResponseEntity<List<AuditLogDTO>> getAuditLogsForUser(@PathVariable Long userId){
        return ResponseEntity.ok(identityService.getAuditLogsForUser(userId));
    }
}
