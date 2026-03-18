package com.labconnect.controller.Identity;

import com.labconnect.DTORequest.Identity.UserRequestDTO;
import com.labconnect.DTORequest.Identity.LoginRequest;

import com.labconnect.DTOResponse.Identity.AuditLogDTO;
import com.labconnect.DTOResponse.Identity.UserResponseDTO;
import com.labconnect.security.JwtService;

import com.labconnect.services.Identity.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="http://localhost:3000")
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            String token = jwtService.generateToken(loginRequest.getEmail());
            return ResponseEntity.ok(token);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }


    @GetMapping("/users")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        return ResponseEntity.ok(identityService.getAllUsers());
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO){
        return new ResponseEntity<>(identityService.createUser(userRequestDTO),HttpStatus.CREATED);
    }

    @GetMapping("/users/{email}")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email){
        return ResponseEntity.ok(identityService.getUserByEmail(email));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO){
        return ResponseEntity.ok(identityService.updateUser(id,userRequestDTO));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        identityService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/audit/{userId}")
    public ResponseEntity<List<AuditLogDTO>> getAuditLogsForUser(@PathVariable Long userId){
        return ResponseEntity.ok(identityService.getAuditLogsForUser(userId));
    }
}
