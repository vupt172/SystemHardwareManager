package com.vupt.SHM.security;

import com.vupt.SHM.entity.Account;
import com.vupt.SHM.exceptions.AppException;
import com.vupt.SHM.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl  implements UserDetailsService {
@Autowired
AccountRepository accountRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account=accountRepository.findByUsername(username).orElseThrow(()-> new AppException("Username is incorrect"));
        return  UserDetailsImpl.build(account);
    }

}
