package io.eddie.accountsservice.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class AccountDetails implements UserDetails {

    private final String accountCode;

    private final String username;
    private final String password;

    private final List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
/*
        List<GrantedAuthority> result = new ArrayList<>();

        for (Role role : roles) {
            String roleName = role.name();
            SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(roleName);
            result.add(grantedAuthority);
        }


        return result;
     */
        return roles.stream()
                .map( r-> new SimpleGrantedAuthority(r.name()))
                .toList();
    }

}
