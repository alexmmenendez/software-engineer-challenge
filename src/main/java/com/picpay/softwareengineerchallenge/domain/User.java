package com.picpay.softwareengineerchallenge.domain;

import lombok.*;
import org.springframework.data.mongodb.core.index.TextIndexed;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1698787879438918689L;
    private String id;
    private String uuid;
    @TextIndexed
    private String name;
    @TextIndexed
    private String username;
    private Integer relevanceLevel;
    private String password;

}
