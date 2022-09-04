package com.ewisselectronic.sulama.sulamaservice.model;

import com.ewisselectronic.sulama.sulamacore.annotation.constraint.ValidPassword;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class CreateUserRequest {

    @NotBlank
    @NotEmpty
    private String name;
    private String surname;
    @NotEmpty
    @NotBlank
    private String username;

    @ValidPassword
    private String password;

    private boolean enabled;
    private boolean admin;
}
