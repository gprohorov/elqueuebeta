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

/*
    private List<Role> roles; //= new ArrayList<>(Arrays.asList(Role.USER));

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

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.findOne(username);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), getAuthority() );
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

    private List<SimpleGrantedAuthority> getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}
