package com.labconnect.security;

import com.labconnect.models.Identity.User;
import com.labconnect.repository.Identity.UserRepository;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDetails userDetails;

    //@InjectMocks
    private JwtService jwtService;

    private final String TEST_EMAIL="auth@labconnect.com";

    @BeforeEach
    void setUp() {
        // Manually injecting the mock via constructor to avoid the NullPointerException
        jwtService = new JwtService(userRepository);
    }

    @Test
    @DisplayName("Verify JWT Expiry is exactly 5 minutes")
    void testTokenDuration(){
        User mockUser=new User();
        mockUser.setEmail(TEST_EMAIL);
        mockUser.setRole(User.Role.Admin);
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(mockUser);

        String token= jwtService.generateToken(TEST_EMAIL);
        Date iat=jwtService.extractClaim(token, Claims::getIssuedAt);
        Date exp=jwtService.extractClaim(token,Claims::getExpiration);

        long diffInSeconds=(exp.getTime()- iat.getTime())/1000;
        assertEquals(300,diffInSeconds,"Expiry should be 300 seconds");
    }

    @Test
    @DisplayName("Verify User Role is correctly embedded in claims (Case Sensitive)")
    void testTokenClaimsRole() {
        // Arrange
        User mockUser = new User();
        mockUser.setRole(User.Role.Technician); // Case sensitive per your Model
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(mockUser);

        // Act
        String token = jwtService.generateToken(TEST_EMAIL);
        String role = jwtService.extractClaim(token, claims -> claims.get("role", String.class));

        // Assert
        assertEquals("Technician", role, "Role claim should match the Enum name exactly");
    }

    @Test
    @DisplayName("Positive: Token validation returns true for valid user")
    void testValidateToken_Success() {
        // Arrange
        User mockUser = new User();
        mockUser.setRole(User.Role.Pathologist);
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(mockUser);
        when(userDetails.getUsername()).thenReturn(TEST_EMAIL);

        // Act
        String token = jwtService.generateToken(TEST_EMAIL);

        // Assert
        assertTrue(jwtService.validateToken(token, userDetails));
    }

}
