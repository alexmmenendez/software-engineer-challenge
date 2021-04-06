package com.picpay.softwareengineerchallenge.controller;

import com.picpay.softwareengineerchallenge.controller.request.JwtRequest;
import com.picpay.softwareengineerchallenge.controller.response.JwtResponse;
import com.picpay.softwareengineerchallenge.controller.response.UserResponse;
import com.picpay.softwareengineerchallenge.services.UserService;
import com.picpay.softwareengineerchallenge.services.auth.JwtAuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

import static com.picpay.softwareengineerchallenge.controller.validation.CustomValidator.*;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final JwtAuthenticationService jwtAuthenticationService;

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public JwtResponse authenticateUser(@RequestBody JwtRequest jwtRequest) throws BadCredentialsException {
        defaultValidateRequest(jwtRequest);
        return jwtAuthenticationService.authenticateUser(jwtRequest.getUsername(), jwtRequest.getPassword());
    }

    @Validated
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<UserResponse> searchUserByKeyword(
            @Valid
            @NotEmpty @RequestParam(value = "keyword") final String keyword,
            @Min(value = 1) @RequestParam(value = "page") final Integer page) {
        return userService.searchUserByKeyword(keyword, page);
    }
}
