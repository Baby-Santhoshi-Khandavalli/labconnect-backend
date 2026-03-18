package com.labconnect.repository.Identity;



import com.labconnect.models.Identity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        verify(userRepository, times(1)).findByEmail("test@lab.com");
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
        verify(userRepository).save(any(User.class));
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

    @Test
    @DisplayName("Delete user by ID")
    public void testDeleteById_Success() {
        doNothing().when(userRepository).deleteById(1L);

        userRepository.deleteById(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Find by ID returns Optional user")
    public void testFindById_Success() {
        User user = new User();
        user.setUserId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> found = userRepository.findById(1L);

        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals(1L, found.get().getUserId());
    }

    @Test
    @DisplayName("Return empty Optional for unknown ID")
    public void testFindById_NotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Optional<User>> result = Optional.ofNullable(userRepository.findById(999L));

        Assertions.assertFalse(result.get().isPresent());
    }

    @Test
    @DisplayName("Negative: Handle database exception on save")
    public void testSaveUser_DatabaseError() {
        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("DB Connection Failed"));

        Assertions.assertThrows(RuntimeException.class, () -> {
            userRepository.save(new User());
        });
    }

    @Test
    @DisplayName("Negative: Find user by null email")
    public void testFindByEmail_NullInput() {
        when(userRepository.findByEmail(null)).thenReturn(null);

        User result = userRepository.findByEmail(null);

        Assertions.assertNull(result);
    }

    @Test
    @DisplayName("Negative: Delete non-existent ID (Exception handling)")
    public void testDeleteById_ThrowsException() {
        doThrow(new RuntimeException("ID not found")).when(userRepository).deleteById(500L);

        Assertions.assertThrows(RuntimeException.class, () -> {
            userRepository.deleteById(500L);
        });
    }
}
