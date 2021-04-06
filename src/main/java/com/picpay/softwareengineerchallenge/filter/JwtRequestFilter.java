package com.picpay.softwareengineerchallenge.filter;

import com.picpay.softwareengineerchallenge.exceptions.BadRequestException;
import com.picpay.softwareengineerchallenge.exceptions.UnauthorizedException;
import com.picpay.softwareengineerchallenge.services.auth.UserDetailsServiceImpl;
import com.picpay.softwareengineerchallenge.utils.JwtTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    JwtTokenUtils jwtTokenUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException, UnauthorizedException {
        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String accesToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            accesToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtils.getUsernameFromToken(accesToken);
            } catch (final IllegalArgumentException e) {
                log.error("Unable to get Access Token: {}", accesToken);
                throw new BadRequestException("Unable to get Access Token", e);
            } catch (final ExpiredJwtException e) {
                log.error("Access Token has expired: {}", accesToken);
                throw new UnauthorizedException("Access Token has expired", e);
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUsername(username);

            if (jwtTokenUtils.validateToken(accesToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
