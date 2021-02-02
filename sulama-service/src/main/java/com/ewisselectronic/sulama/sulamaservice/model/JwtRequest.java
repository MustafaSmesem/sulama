package com.ewisselectronic.sulama.sulamaservice.model;

import lombok.Data;

@Data
public class JwtRequest {
	private String username;
	private String password;
}
