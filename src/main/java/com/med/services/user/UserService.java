package com.med.services.user;

import com.med.model.Role;
import com.med.model.User;
import com.med.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by george on 30.04.18.
 */
@Service
public class UserService implements UserDetailsService {
    private List<Role> roles = new ArrayList<>(Arrays.asList(Role.USER));



    @Autowired
    UserRepository userRepository;



/*    private  List<User> users = new ArrayList<>(
            Arrays.asList(

                    new User(roles,
                            "user",
                            "user",
                            true,
                            true,
                            true,
                            true) ,
                    new User(roles,
                            "admin",
                            "admin",
                            true,
                            true,
                            true,
                            true) ,
                    new User(roles,
                            "root",
                            "root",
                            true,
                            true,
                            true,
                            true)


            )
    );



    @PostConstruct
    void init(){
       userRepository.saveAll(users);
    }
  */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepository.findAll()
               .stream().filter(usr -> usr.getUsername().equals(username))
               .findAny().orElseThrow(()
                       -> new UsernameNotFoundException( username + " was not found") );

        return user;
    }

    public Token getTokenForUser(User user){

        return null;
    }
}
