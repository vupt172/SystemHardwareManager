package com.vupt.SHM.security;

import com.vupt.SHM.entity.Account;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
public class UserDetailsImpl implements UserDetails {

    private  long id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    public UserDetailsImpl(long id, String username, String password, Collection<? extends GrantedAuthority> authorities){
        this.id=id;
        this.username=username;
        this.password=password;
        this.authorities=authorities;

    }
    public static UserDetailsImpl build(Account account){
        List<GrantedAuthority> authorityList = account.getRoles().stream()
                .map(role->new SimpleGrantedAuthority(role.getAuthority().name()))
                .collect(Collectors.toList());

    return new UserDetailsImpl(account.getId(),account.getUsername(),account.getPassword(),authorityList);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }

}
