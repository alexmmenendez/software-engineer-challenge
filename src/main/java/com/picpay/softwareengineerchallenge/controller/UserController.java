package com.picpay.softwareengineerchallenge.controller;

import com.picpay.softwareengineerchallenge.controller.request.JwtRequest;
import com.picpay.softwareengineerchallenge.controller.response.JwtResponse;
import com.picpay.softwareengineerchallenge.controller.response.UserResponse;
import com.picpay.softwareengineerchallenge.services.UserService;
import com.picpay.softwareengineerchallenge.services.auth.JwtAuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final JwtAuthenticationService jwtAuthenticationService;

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public JwtResponse createAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        return jwtAuthenticationService.authenticateUser(jwtRequest.getUsername(), jwtRequest.getPassword());
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<UserResponse> searchUserByKeyword(final String keyword, final Integer page) throws Exception {
        return userService.searchUserByKeyword(keyword, page);
    }
}
