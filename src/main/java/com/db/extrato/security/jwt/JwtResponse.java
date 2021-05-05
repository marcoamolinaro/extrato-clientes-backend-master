package com.db.extrato.security.jwt;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = 1L;


	String token;
}
