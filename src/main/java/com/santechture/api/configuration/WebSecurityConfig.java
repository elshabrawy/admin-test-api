package com.smartTec.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.smartTec.models.security.jwt.AuthEntryPointJwt;
import com.smartTec.models.security.jwt.AuthTokenFilter;
import com.smartTec.models.security.services.UserDetailsServiceImpl;


@Configuration
@EnableMethodSecurity
//@EnableWebMvc
public class WebSecurityConfig {
  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }
  
  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
       
      authProvider.setUserDetailsService(userDetailsService);
      authProvider.setPasswordEncoder(passwordEncoder());
   
      return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    DelegatingPasswordEncoder delPasswordEncoder =
            (DelegatingPasswordEncoder) PasswordEncoderFactories
                    .createDelegatingPasswordEncoder();
    BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
    delPasswordEncoder.setDefaultPasswordEncoderForMatches(bcryptPasswordEncoder);
    return delPasswordEncoder;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf->csrf.disable())
            .exceptionHandling(exception->exception.authenticationEntryPoint(unauthorizedHandler))
            .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth->
                            auth.requestMatchers("/api/auth/**").permitAll()
                                    .requestMatchers("/api/test/**").permitAll()
                                    .requestMatchers("/v3/**").permitAll()
                                    .requestMatchers("/**").permitAll()
//                  .requestMatchers("/swagger-ui/**").permitAll()
//                  .requestMatchers("/api-docs/**").permitAll()
//                  .requestMatchers("**/resources/**").permitAll()
//                  .requestMatchers("/resources/**").permitAll()
//                  .requestMatchers("resources/**").permitAll()
//                  .requestMatchers("META-INF/resources/**").permitAll()
//                  .requestMatchers("**/static/**").permitAll()
//                  .requestMatchers("/webjars/**").permitAll()
//                  .requestMatchers("**/swagger-ui.html").permitAll()
                                         .anyRequest().authenticated()
            );
    
    http.authenticationProvider(authenticationProvider());

    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    
    return http.build();
  }
}
