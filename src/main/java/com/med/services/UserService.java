package com.med.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.med.model.Doctor;
import com.med.model.User;
import com.med.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
    @Autowired
    UserRepository userRepository;

    @Autowired
    DoctorService doctorService;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.findOne(username);
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(), user.getPassword(), getAuthority(user) );
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findOne(String username) {
        return userRepository.findAll().stream().filter(usr -> usr.getUsername().equals(username))
            .findAny().orElseThrow(() -> new UsernameNotFoundException(username + " was not found") );
    }

    public Doctor getCurrentUserInfo() {
        return doctorService.getDoctorByUserId(
            this.findOne(SecurityContextHolder.getContext().getAuthentication().getName()).getId()
        );
    }
    
    public List<SimpleGrantedAuthority> getAuthority(User user) {
        return Arrays.asList(new SimpleGrantedAuthority(user.getAuthorities().get(0).name()));
    }
}