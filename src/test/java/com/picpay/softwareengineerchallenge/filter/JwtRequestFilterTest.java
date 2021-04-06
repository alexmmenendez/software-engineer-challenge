package com.picpay.softwareengineerchallenge.filter;

import com.picpay.softwareengineerchallenge.configs.CollectionIndexConfig;
import com.picpay.softwareengineerchallenge.exceptions.BadRequestException;
import com.picpay.softwareengineerchallenge.exceptions.UnauthorizedException;
import com.picpay.softwareengineerchallenge.services.auth.UserDetailsServiceImpl;
import com.picpay.softwareengineerchallenge.utils.JwtTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class JwtRequestFilterTest {

    @InjectMocks
    private JwtRequestFilter jwtRequestFilter;
    @Mock
    private FilterChain filterChain;
    @Mock
    private UserDetails userDetails;
    @Mock
    private JwtTokenUtils jwtTokenUtils;
    @Mock
    private UserDetailsServiceImpl userDetailsService;
    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @MockBean
    private CollectionIndexConfig collectionIndexConfig;

    private static final String USERNAME = "diether.bein";
    private static final String ACCESS_TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9" +
            ".eyJzdWIiOiJhbGV4Lm1pbGxlciIsImV4cCI6MTYxNzUyNTY2OCwiaWF0IjoxNjE3NTA3NjY4fQ." +
            "InjcMFaweo55nsGyLL6-mPEHUL8UkP7Rd9_cjXhJye7bJTMrT3gpW-UCPQZ_CvEhBTldgn6SSxdMlZaAtpi5Vg";

    @Test
    public void shouldDoFilterInternal() throws ServletException, IOException {
        when(httpServletRequest.getHeader("Authorization")).thenReturn(ACCESS_TOKEN);
        when(jwtTokenUtils.getUsernameFromToken(ACCESS_TOKEN.substring(7))).thenReturn(USERNAME);
        when(jwtTokenUtils.validateToken(anyString(), any())).thenReturn(Boolean.TRUE);
        when(userDetailsService.loadUserByUsername(USERNAME)).thenReturn(userDetails);
        jwtRequestFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionDoFilterInternal() {
        when(httpServletRequest.getHeader("Authorization")).thenReturn(ACCESS_TOKEN);
        when(jwtTokenUtils.getUsernameFromToken(ACCESS_TOKEN.substring(7))).thenThrow(IllegalArgumentException.class);
        assertThrows(BadRequestException.class,
                () -> jwtRequestFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain)
        );
    }

    @Test
    public void shouldThrowUnauthorizedExceptionDoFilterInternalBecauseAccessTokenHasExpired() {
        when(httpServletRequest.getHeader("Authorization")).thenReturn(ACCESS_TOKEN);
        when(jwtTokenUtils.getUsernameFromToken(ACCESS_TOKEN.substring(7))).thenThrow(ExpiredJwtException.class);

        assertThrows(UnauthorizedException.class,
                () -> jwtRequestFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain)
        );
    }
}
