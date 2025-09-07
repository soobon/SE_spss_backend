package com.example.SE_project.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SpringSecurityConfig {

    private JwtAuthFilter jwtAuthFilter;

    private CorsConfig corsConfig;

    private AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.csrf(AbstractHttpConfigurer::disable);

//        http.cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()));

        http.authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/auth/authen").permitAll()
//                        .requestMatchers("/auth/logout").permitAll()
//                                .requestMatchers("/student/**").hasAuthority("USER")
//                                .requestMatchers("/admin/**").hasAuthority("ADMIN")
//                        .anyRequest().authenticated()
                                .anyRequest().permitAll()
        )
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .authenticationProvider(authenticationProvider);

        return http.build();
    }


}
