package com.ewisselectronic.sulama.sulamaservice.model.user;

import com.ewisselectronic.sulama.sulamacore.annotation.constraint.ValidPassword;
import com.ewisselectronic.sulama.sulamacore.annotation.constraint.ValidUsername;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class CreateUserRequest {

    @NotBlank
    @NotEmpty
    private String name;
    private String surname;
    @ValidUsername
    private String username;

    @ValidPassword
    private String password;

    private boolean enabled;
    private boolean admin;
}
