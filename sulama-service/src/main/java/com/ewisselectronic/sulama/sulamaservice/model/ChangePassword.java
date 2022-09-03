package com.ewisselectronic.sulama.sulamaservice.model;

import com.ewisselectronic.sulama.sulamacore.annotation.constraint.ValidPassword;
import lombok.Data;

@Data
public class ChangePassword {
    @ValidPassword
    private String password;
    private String passwordConfirm;
}
