package com.picpay.softwareengineerchallenge.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
