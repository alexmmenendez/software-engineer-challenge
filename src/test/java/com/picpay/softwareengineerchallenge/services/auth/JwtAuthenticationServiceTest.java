package com.picpay.softwareengineerchallenge.services.auth;

import com.picpay.softwareengineerchallenge.configs.CollectionIndexConfig;
import com.picpay.softwareengineerchallenge.utils.JwtTokenUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class JwtAuthenticationServiceTest {

    @InjectMocks
    private JwtAuthenticationService jwtAuthenticationService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtTokenUtils jwtTokenUtils;
    @MockBean
    private CollectionIndexConfig collectionIndexConfig;

    private static final String accessToken = "accessToken";
    private static final String password = "diether.bein";
    private static final String username = "!picPAY@2021";

    @Test
    public void shouldAuthenticateUserSuccessfully() {
        when(jwtTokenUtils.generateToken(username)).thenReturn(accessToken);
        assertEquals(accessToken, jwtAuthenticationService.authenticateUser(username, password).getAccessToken());
    }

    @Test
    public void shouldThrowBadCredentialsExceptionInvokingAuthenticate() {
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password)))
                .thenThrow(BadCredentialsException.class);
        assertThrows(BadCredentialsException.class,
                () -> jwtAuthenticationService.authenticateUser(username, password)
        );
    }

}
