package com.med.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.med.config.JwtTokenUtil;
import com.med.datastorage.Support;
import com.med.model.Doctor;
import com.med.model.LoginUser;
import com.med.model.User;
import com.med.services.doctor.impls.DoctorServiceImpl;
import com.med.services.user.UserService;

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

    @Autowired
    private Support supportBean;

    @PostMapping("/authenticate")
    public ResponseEntity<?> register(@RequestBody LoginUser loginUser) throws AuthenticationException {
        supportBean.check();
        final Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final User user = userService.findOne(loginUser.getUsername());
        final String token = jwtTokenUtil.generateToken(user);
        final Doctor doctor = doctorService.getDoctorByUserId(user.getId());
        user.setInfo(doctor);
        user.setToken(token);
        return ResponseEntity.ok(user);
    }
}