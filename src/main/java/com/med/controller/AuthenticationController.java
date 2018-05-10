package com.med.controller;

import com.med.config.JwtTokenUtil;
import com.med.model.Doctor;
import com.med.model.LoginUser;
import com.med.model.User;
import com.med.services.user.UserService;
import com.med.services.doctor.impls.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private DoctorServiceImpl doctorService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> register(@RequestBody LoginUser loginUser) throws AuthenticationException {

        // BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // System.out.println(encoder.encode("password"));

        final Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginUser.getUsername(),
                loginUser.getPassword()
            )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final User user = userService.findOne(loginUser.getUsername());
        final Doctor doctor = doctorService.getDoctorByUserId(user.getId());
        final String token = jwtTokenUtil.generateToken(user);
        user.setToken(token);
        user.setInfo(doctor);
        return ResponseEntity.ok(user);
    }

}