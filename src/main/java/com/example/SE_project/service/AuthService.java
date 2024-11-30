package com.example.SE_project.service;

import com.example.SE_project.dto.AuthRequest;
import com.example.SE_project.dto.AuthResponse;
import com.example.SE_project.dto.RegisterRequest;
import com.example.SE_project.entity.Account;
import com.example.SE_project.entity.enum_class.Role;
import com.example.SE_project.exception.UserNotFound;
import com.example.SE_project.reposistory.AccountRepository;
import com.example.SE_project.reposistory.AdminRepository;
import com.example.SE_project.reposistory.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthService {

    private AccountRepository accountRepository;

//    private PasswordEncoder passwordEncoder;

    private JwtService jwtService;

    private AuthenticationManager authenticationManager;

    private AdminRepository adminRepository;

    private StudentRepository studentRepository;

//    public AuthResponse register(RegisterRequest request){
//        User user= User.builder()
//                .firstName(request.getFirstName())
//                .lastName(request.getLastName())
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .role(Role.USER)
//                .build();
//        userRepository.save(user);
//        String jwtToken = jwtService.generateToken(user);
//        return AuthResponse.builder()
//                .token(jwtToken)
//                .build();
//    }

    public AuthResponse auth(AuthRequest request){

        if (accountRepository.findByUsername(request.getUsername()).isEmpty())
            return new AuthResponse(null,null,"Invalid Username: Can't find Username");

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            return new AuthResponse(null,null, "Invalid Password: Wrong password");
        }

        Account account = accountRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new UserNotFound("Cant find user")
        );

        String userId = "";
        if (account.getWeb_role().equals(Role.ADMIN)){
            userId = adminRepository.findByAccount(account).getId();
        }else if (account.getWeb_role().equals(Role.USER)){
            userId = studentRepository.findByAccount(account).getId();
        }

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", account.getWeb_role());// Ví dụ thêm role của user
        extraClaims.put("user_id", userId);

        String jwtToken = jwtService.generateToken(extraClaims,account);
        System.out.println("token: " + jwtToken);
        return AuthResponse.builder()
                .token(jwtToken)
                .user_id(userId)
                .response("Login successfully!")
                .build();
    }

    public String getIdFromToken(){
        return null;
    }
}
