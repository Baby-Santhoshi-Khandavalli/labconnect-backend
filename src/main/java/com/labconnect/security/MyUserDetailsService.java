package com.labconnect.security;

import com.labconnect.models.User;
import com.labconnect.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user=userRepository.findByEmail(email);
//        if(user==null){
//            throw new UsernameNotFoundException("User not found with email: "+email);
//        }
//        return new UserPrincipal(user);
//    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email.toLowerCase().trim());
        if (user == null) {
            System.out.println("DEBUG: User NOT found in DB with email: " + email);
            throw new UsernameNotFoundException("User not found");
        }
        System.out.println("DEBUG: User found! Hashed password in DB is: " + user.getPassword());
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                new ArrayList<>() // Or your roles
        );
    }
}
