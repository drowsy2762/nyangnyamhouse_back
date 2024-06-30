//package com.goorm.nyangnyam_back.controller;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//import com.goorm.nyangnyam_back.entity.User;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//public class AuthController {
//
//    private final AuthenticationManager authenticationManager;
//    private final UserDetailsService userDetailsService;
//
//    public AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
//        this.authenticationManager = authenticationManager;
//        this.userDetailsService = userDetailsService;
//    }
//
//    @PostMapping("/login")
//    public String login(@RequestBody User user) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
//        );
//
//        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
//
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("sub", userDetails.getUsername());
//        claims.put("roles", userDetails.getAuthorities());
//
//        String jwtToken = Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
//                .signWith(SignatureAlgorithm.HS256, "secret")
//                .compact();
//
//        return jwtToken;
//    }
//}