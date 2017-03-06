package com.oakwood.security.service;

import com.oakwood.security.JwtUserFactory;
import com.oakwood.security.model.repo.Authority;
import com.oakwood.security.model.repo.User;
import com.oakwood.security.repository.UserRepository;
import org.onehippo.forge.security.support.springsecurity.authentication.HippoUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by stephan on 20.03.16.
 */
@Service
public class JwtUserDetailsServiceImpl extends HippoUserDetailsServiceImpl {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails userDetails = super.loadUserByUsername(username);

        User user = userRepository.findByUsername(username);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        List<Authority> authoritiesUser = authorities.stream()
                .map(authority -> new Authority(authority.getAuthority()))
                .collect(Collectors.toList());

        user.setAuthorities(authoritiesUser);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return JwtUserFactory.create(user);
        }
    }
}
