package com.santechture.api.service;

import com.santechture.api.entity.Admin;
import com.santechture.api.repository.AdminRepository;
import com.santechture.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository; // Implement this repository
    @Autowired
    private UserRepository userRepository;   // Implement this repository

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Search for admin by username
            Admin admin = adminRepository.findByUsernameIgnoreCase(username);
            List<GrantedAuthority> adminAuthorities = new ArrayList<>();
            adminAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            return new User(admin.getUsername(), admin.getPassword(), adminAuthorities);

//
//        // Search for user by username
//        User user = userRepository.existsByUsernameIgnoreCase(username);
//            List<GrantedAuthority> userAuthorities = new ArrayList<>();
//        userAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//            return new User(user.getUsername(), user.getPassword(), userAuthorities);
//
//
//        throw new UsernameNotFoundException("User not found with username: " + username);
    }

}
