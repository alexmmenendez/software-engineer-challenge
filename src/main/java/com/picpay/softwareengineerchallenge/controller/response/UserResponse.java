package com.picpay.softwareengineerchallenge.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private String uuid;
    private String name;
    private String username;
    private Integer relevanceLevel;
}
