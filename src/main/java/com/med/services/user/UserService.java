package com.med.services.user;

import com.med.model.Role;
import com.med.model.User;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User(roles,
                "user",
                username,
                true,
                true,
                true,
                true);
    }
}
