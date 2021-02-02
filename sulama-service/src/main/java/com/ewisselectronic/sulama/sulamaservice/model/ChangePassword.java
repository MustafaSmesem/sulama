package com.ewisselectronic.sulama.sulamaservice.model;

import lombok.Data;

@Data
public class ChangePassword {
    private final Integer id;
    private final String password;
    private final String passwordConfirm;
}
