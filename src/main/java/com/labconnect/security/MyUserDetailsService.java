package com.labconnect.security;

import com.labconnect.models.Identity.User;
import com.labconnect.repository.Identity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {


        User user = userRepository.findByEmail(email.toLowerCase().trim());

        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + email);
        }

        // Convert enum (Pathologist) → ROLE_PATHOLOGIST
        String roleName = "ROLE_" + user.getRole().name().toUpperCase();

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roleName);

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(authority)   // <-- ROLE ATTACHED HERE
        );
    }
}