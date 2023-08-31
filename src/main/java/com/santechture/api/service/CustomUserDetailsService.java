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
import java.util.Optional;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Search for admin by username
        Optional<Admin> adminOptional = adminRepository.findByUsernameIgnoreCase(username);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            return new User(admin.getUsername(), admin.getPassword(), authorities);
        }

        // Search for user by username
        Optional<com.santechture.api.entity.User> userOptional = userRepository.findByUsernameIgnoreCase(username);
        if (userOptional.isPresent()) {
            com.santechture.api.entity.User user = userOptional.get();
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new User(user.getUsername(), "Default Password", authorities);
        }

        throw new UsernameNotFoundException("User not found with username: " + username);

    }

}
