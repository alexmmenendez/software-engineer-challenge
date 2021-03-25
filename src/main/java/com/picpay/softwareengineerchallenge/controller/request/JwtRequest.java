package com.picpay.softwareengineerchallenge.controller.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class JwtRequest {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
