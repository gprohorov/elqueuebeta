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
import com.med.model.Role;
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
    
    public User findByIdWithDoctor(String id) {
    	User user = userRepository.findById(id).get();
    	user.setInfo(doctorService.getDoctorByUserId(id));
    	return user;
    }
    
    public void deleteById(String id) {
    	User user = userRepository.findById(id).orElse(null);
    	if (user == null) return;
    	if (user.getAuthorities().contains(Role.ROLE_SUPERADMIN)) return;
    	userRepository.deleteById(id);
    	Doctor doctor = doctorService.getDoctorByUserId(id);
    	if (doctor != null) { 
    		doctor.setUserId(null);
    		doctorService.updateDoctor(doctor);
    	}
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    public List<User> findAllWithDoctors() {
    	List<User> users = userRepository.findAll();
    	users.stream().forEach(user -> { user.setInfo(doctorService.getDoctorByUserId(user.getId())); });
    	return users;
    }

    public User findOne(String username) {
        return userRepository.findAll().stream().filter(usr -> usr.getUsername().equals(username))
            .findAny().orElseThrow(() -> new UsernameNotFoundException(username + " was not found") );
    }

    public Doctor getCurrentUserInfo() {
        if (this.isSuperAdmin()) return doctorService.getDoctor(1);
        if (this.isAdmin()) return doctorService.getDoctor(2);
    	return doctorService.getDoctorByUserId(
            this.findOne(SecurityContextHolder.getContext().getAuthentication().getName()).getId()
        );
    }
    
    public List<SimpleGrantedAuthority> getAuthority(User user) {
        return Arrays.asList(new SimpleGrantedAuthority(user.getAuthorities().get(0).name()));
    }
    
    public Boolean isSuperAdmin() {
    	return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
			.contains(new SimpleGrantedAuthority(Role.ROLE_SUPERADMIN.getAuthority()));
    }
    
    public Boolean isAdmin() {
    	return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
			.contains(new SimpleGrantedAuthority(Role.ROLE_ADMIN.getAuthority()));
    }
    
    public Boolean isDoctor() {
    	return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
			.contains(new SimpleGrantedAuthority(Role.ROLE_DOCTOR.getAuthority()));
    }
}