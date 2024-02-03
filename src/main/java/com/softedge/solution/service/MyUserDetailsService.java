package com.softedge.solution.service;

import com.softedge.solution.models.UserRegistration;
import com.softedge.solution.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new User("foo", "foo",
                new ArrayList<>());
    }

    @Transactional(readOnly = true)
    public UserRegistration getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }
}