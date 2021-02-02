package com.ewisselectronic.sulama.sulamaservice.model;

import lombok.Data;

@Data
public class SaveResponse {
    private final boolean state;
    private final String message;
    private final Long id;
}
