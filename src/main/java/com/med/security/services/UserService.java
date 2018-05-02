package com.med.security.services;

import com.med.security.model.Role;
import com.med.security.model.User;
import com.med.security.repository.UserRepository;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
    private List<Role> roles = new ArrayList<>(Arrays.asList(Role.USER));

    @Autowired
    UserRepository userRepository;

/*
    private  List<User> users = new ArrayList<>(
            Arrays.asList(

                    new User(roles, "user", "user",   true, true, true, true) ,
                    new User(roles, "admin", "admin", true, true, true, true) ,
                    new User(roles, "root", "root",   true, true, true, true),
                    new User(roles, "superadmin", "sadmin",   true, true, true, true)
            )
    );

    @PostConstruct
    void init(){
       userRepository.saveAll(users);
    }

*/

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
       User user = userRepository.findAll()
               .stream().filter(usr -> usr.getUsername().equals(username))
               .findAny().orElseThrow(()
                       -> new UsernameNotFoundException( username + " was not found") );
        return user;
    }
        public Optional<User> findById(@NonNull ObjectId id) {
            return userRepository.findById(id);
        }

        public List<User> findAll(){
        return userRepository.findAll();
        }
}
