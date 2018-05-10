package com.med.services.user;

import com.med.model.User;
import com.med.repository.user.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


/**
 * Created by george on 30.04.18.
 */
@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    private ArrayList<String> roles;
/*
    @PostConstruct
    void init() {
        User user = new User(new ArrayList<String>(Arrays.asList("ROLE_ADMIN")),
                new BCryptPasswordEncoder().encode("admin"),
                "admin");
        userRepository.save(user);
    }
*/
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.findOne(username);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), getAuthority(user) );
    }

    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findOne(String username) {
        return userRepository.findAll()
                .stream().filter(usr -> usr.getUsername().equals(username))
                .findAny().orElseThrow(()
                        -> new UsernameNotFoundException( username + " was not found") );
    }

    private List<SimpleGrantedAuthority> getAuthority(User user) {
        return Arrays.asList(new SimpleGrantedAuthority(user.getAuthorities().get(0)));
    }
}
