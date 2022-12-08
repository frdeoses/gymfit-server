package com.uma.gymfit.calendar.security.service.impl;

import com.uma.gymfit.calendar.model.user.User;
import com.uma.gymfit.calendar.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);

        if(user == null){
            log.error("Usuario no encontrado...");
            throw  new UsernameNotFoundException("Usuario no encontrado");
        }

        return user;
    }
}
