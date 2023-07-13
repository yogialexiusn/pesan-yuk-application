package com.example.demo.Services;

import com.example.demo.Models.User;
import com.example.demo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Component("UserDetailServiceImpl")
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found bro "+ username));
        return UserPrinciple.build(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id){
        User user = userRepository.getById(id);
        return UserPrinciple.build(user);
    }
}
