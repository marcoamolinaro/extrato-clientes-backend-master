package com.db.extrato.security.login;

import com.db.extrato.security.jwt.JwtRequest;
import org.springframework.http.ResponseEntity;

public interface Login {

  ResponseEntity<?> login(JwtRequest jwtRequest) throws Exception;

}
