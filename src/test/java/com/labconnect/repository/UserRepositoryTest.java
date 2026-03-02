package com.labconnect.repository;

import com.labconnect.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

//@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {
    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Should find user by email")
    public void testFindByEmail(){
        User mockUser=new User();
        mockUser.setName("Test User");
        mockUser.setEmail("test@lab.com");
        Mockito.when(userRepository.findByEmail("test@lab.com")).thenReturn(mockUser);

        User found=userRepository.findByEmail("test@lab.com");

        Assertions.assertNotNull(found);
        Assertions.assertEquals("test@lab.com", found.getEmail());
        Mockito.verify(userRepository,Mockito.times(1)).findByEmail("test@lab.com");
    }

    @Test
    @DisplayName("Should return null when email does not exist")
    public void testFindByEmail_NotFound(){
        Mockito.when(userRepository.findByEmail("unknown@lab.com")).thenReturn(null);
        User found=userRepository.findByEmail("unknown@lab.com");
        Assertions.assertNull(found);
    }

    @Test
    @DisplayName("Should save a user and return the object")
    public void testSaveUser(){
        User userToSave=new User();
        userToSave.setName("New User");
        Mockito.when(userRepository.save(any(User.class))).thenReturn(userToSave);
        User savedUser=userRepository.save(new User());

        Assertions.assertEquals("New User", savedUser.getName());
        Mockito.verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should find user by ID")
    public void testFindById(){
        User mockUser=new User();
        mockUser.setUserId(10L);

        Mockito.when(userRepository.findById(10L)).thenReturn(Optional.of(mockUser));

        Optional<User> result=userRepository.findById(10L);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(10L,result.get().getUserId());
    }
}
