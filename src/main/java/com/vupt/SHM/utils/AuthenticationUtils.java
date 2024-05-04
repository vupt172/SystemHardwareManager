package com.vupt.SHM.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticationUtils {
    private Authentication authentication;
    public AuthenticationUtils(Authentication authentication){
        this.authentication=authentication;
    }
    public boolean hasRole_Admin(){
        return authentication.getAuthorities().stream().anyMatch(authority->authority.getAuthority().equals("ROLE_ADMIN"));
    }
    public static UserDetails getUserDetails(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        return authentication==null?null:(UserDetails) authentication.getPrincipal();
    }

}

