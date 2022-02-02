package com.seansforum.controllers;

import com.seansforum.entity.User;
import com.seansforum.repository.UserRepo;
import com.seansforum.models.LoginCredentials;
import com.seansforum.security.JWTUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired private UserRepo userRepo;
    @Autowired private JWTUtil jwtUtil;
    @Autowired private AuthenticationManager authManager;
    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public Map<String, Object> registerHandler(@RequestBody User user){
        String encodedPass = passwordEncoder.encode(user.getPswd());
        user.setPswd(encodedPass);
        user = userRepo.save(user);
        String result = "User is registered";
        return Collections.singletonMap("result", result);
    }

    @PostMapping("/login")
    public Map<String, Object> loginHandler(@RequestBody LoginCredentials body){
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getLogin(), body.getPswd());

            authManager.authenticate(authInputToken);

            String token = jwtUtil.generateToken(body.getLogin());

            return Collections.singletonMap("jwtToken", token);
        }catch (AuthenticationException authExc){
            throw new RuntimeException("Incorrect login or password", authExc);
        }
    }
}
