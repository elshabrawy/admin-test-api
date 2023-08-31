package com.santechture.api.service;

import com.santechture.api.dto.GeneralResponse;
import com.santechture.api.dto.admin.AdminDto;
import com.santechture.api.entity.Admin;
import com.santechture.api.exception.BusinessExceptions;
import com.santechture.api.repository.AdminRepository;
import com.santechture.api.util.JwtUtils;
import com.santechture.api.validation.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService {


    private final AdminRepository adminRepository;

    private final BCryptPasswordEncoder passwordEncoder ;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;


    public AdminService(AdminRepository adminRepository,JwtUtils jwtUtils,AuthenticationManager authenticationManager) {
        this.adminRepository = adminRepository;
        this.jwtUtils=jwtUtils;
        this.authenticationManager=authenticationManager;
        this.passwordEncoder = new BCryptPasswordEncoder();

    }

    public ResponseEntity<GeneralResponse> login(LoginRequest request) throws BusinessExceptions {

        Optional<Admin> adminOptional = adminRepository.findByUsernameIgnoreCase(request.getUsername());
          if (adminOptional.isEmpty()) {
            throw new BusinessExceptions("login.credentials.not.match");
        }

        Admin admin = adminOptional.get();
        String storedHashedPassword = admin.getPassword();
        String providedPassword = request.getPassword();

        if (!passwordEncoder.matches(providedPassword, storedHashedPassword)) {
            throw new BusinessExceptions("login.credentials.not.match");
        }

        List<String> roles = List.of("ROLE_ADMIN","ROLE_USER");
        String jwt = jwtUtils.generateJwtToken(admin,roles);

        AdminDto adminDto=new AdminDto(admin);
        adminDto.setAccessToken(jwt);
        return new GeneralResponse().response(adminDto);

}
}
