package org.example.emergency.service.impls;

import lombok.RequiredArgsConstructor;
import org.example.emergency.entity.Caller;
import org.example.emergency.entity.Role;
import org.example.emergency.repository.CallerRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final CallerRepository callerRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Caller caller = callerRepository.findByUsername(username).orElseThrow(() -> {
            return new UsernameNotFoundException("The caller with username " + username + " does not exist.");
        });

        return new org.springframework.security.core.userdetails.User(caller.getUsername(), caller.getPassword(),
                mapRolesToAuthorities(caller.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream().map((role -> new SimpleGrantedAuthority(role.getName()))).collect(Collectors.toList());
    }
}