package com.ewisselectronic.sulama.sulamaservice.model;

import lombok.Data;

@Data
public class JwtResponse {
    private final Integer id;
    private final String username;
    private final String fullName;
    private final String token;
    private final boolean admin;
}
