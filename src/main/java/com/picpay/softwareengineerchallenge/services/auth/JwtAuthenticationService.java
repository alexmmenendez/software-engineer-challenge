package com.picpay.softwareengineerchallenge.services.auth;

import com.picpay.softwareengineerchallenge.controller.response.JwtResponse;
import com.picpay.softwareengineerchallenge.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtAuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtil;

    public JwtResponse authenticateUser(final String username, final String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (final BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS");
        }

        return JwtResponse.builder().accessToken(jwtTokenUtil.generateToken(username)).build();
    }
}
