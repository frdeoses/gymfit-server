package com.uma.gymfit.trainingtable.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IUserDetailsService {
    public UserDetails loaUserByUsername(String name) throws UsernameNotFoundException;
}
