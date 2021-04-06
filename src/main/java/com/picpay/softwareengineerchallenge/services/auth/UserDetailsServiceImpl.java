package com.picpay.softwareengineerchallenge.services.auth;

import com.picpay.softwareengineerchallenge.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    public static final String ROLE_USER = "ROLE_USER";

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        log.info("Loading user by username: {}", username);
        final String password = userService.findUserByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username))
                .getPassword();
        return new User(username, password, AuthorityUtils.createAuthorityList(ROLE_USER));
    }
}
