package com.uma.gymfit.calendar.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IUserDetailsService {
    public UserDetails loaUserByUsername(String name) throws UsernameNotFoundException;
}
