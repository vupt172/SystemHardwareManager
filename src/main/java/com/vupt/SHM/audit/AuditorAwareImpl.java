package com.vupt.SHM.audit;

import com.vupt.SHM.utils.AuthenticationUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username= authentication==null?"":AuthenticationUtils.getUserDetails().getUsername();
        return Optional.of(username);
        // Use below commented code when will use Spring Security.
    }
}

// ------------------ Use below code for spring security --------------------------

/*
class SpringSecurityAuditorAware implements AuditorAware<User> {

 public User getCurrentAuditor() {

  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

  if (authentication == null || !authentication.isAuthenticated()) {
   return null;
  }

  return ((MyUserDetails) authentication.getPrincipal()).getUser();
 }
 }
 */
