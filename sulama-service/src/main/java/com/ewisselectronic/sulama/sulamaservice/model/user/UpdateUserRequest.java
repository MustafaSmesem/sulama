package com.ewisselectronic.sulama.sulamaservice.model.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateUserRequest {

    @NotNull
    private Integer id;

    @NotBlank
    @NotEmpty
    private String name;
    private String surname;

    private boolean enabled;
    private boolean admin;
}
