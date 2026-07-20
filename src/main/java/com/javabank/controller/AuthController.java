package com.javabank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.javabank.dto.JwtResponse;
import com.javabank.dto.LoginRequest;
import com.javabank.dto.RegisterRequest;
import com.javabank.entity.User;
import com.javabank.jwt.JwtService;
import com.javabank.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public AuthController(
            UserService userService,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {

        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }


    @PostMapping("/register")
    public ResponseEntity<User> registerUser(
            @Valid @RequestBody RegisterRequest request) {


        User user = userService.registerUser(request);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }



    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
            @Valid @RequestBody LoginRequest request) {


    	Authentication authentication =
    	        authenticationManager.authenticate(
    	                new UsernamePasswordAuthenticationToken(
    	                        request.getUsername(),
    	                        request.getPassword()
    	                )
    	        );


        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();


        String token =
                jwtService.generateToken(userDetails);


        return ResponseEntity.ok(
                new JwtResponse(token)
        );
    }

}