package com.db.extrato.security.login;

import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.db.extrato.exception.GenericException;
import com.db.extrato.security.jwt.JwtRequest;
import com.db.extrato.security.jwt.JwtResponse;
import com.db.extrato.security.jwt.JwtTokenUtil;
import com.db.extrato.security.jwt.JwtUserDetailsService;
import com.db.extrato.util.Constants;
import com.db.extrato.util.secserver.SecServerExtrato;
import com.db.extrato.util.secserver.UserExtratoSecServer;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

@Log
@RestController
@CrossOrigin
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginREST implements Login {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  JwtTokenUtil jwtTokenUtil;

  @Autowired
  JwtUserDetailsService userDetailsService;
  
  //@Autowired 
  //UserExtratoService userExtratoService;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  @RequestMapping(value = Constants.LOGIN_PATH, method = RequestMethod.POST)
  public ResponseEntity<?> login(@RequestBody JwtRequest authenticationRequest) throws Exception {
    //TODO: Aqui seria a chamada para autenticar no SecServer e gravar os detalhes do usuário localmente em cache
				
	UserExtratoSecServer uess = SecServerExtrato.getUserExtrato(authenticationRequest.getUsername()); 
		
	if (uess == null) {
		throw new GenericException(Constants.MSG_ERRO_SENHA_USUARIO_INVALIDO);				
	}
	
	log.info("UESS [" + uess.toString() + "]");
		
	boolean isPasswordMatch = bCryptPasswordEncoder.matches(authenticationRequest.getPassword(), uess.getPassword());

	if (!isPasswordMatch) {
		throw new GenericException(Constants.MSG_ERRO_SENHA_USUARIO_INVALIDO);		
	}
	
    authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
    final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
    final String token = jwtTokenUtil.generateToken(userDetails);
    return ResponseEntity.ok(new JwtResponse(token));
  }

  private void authenticate(String username, String password) throws Exception {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException de) {
      log.log(Level.SEVERE, de.getMessage(), de);
      throw new Exception("USER_DISABLED", de);
    } catch (BadCredentialsException be) {
      log.log(Level.SEVERE, be.getMessage(), be);
      throw new Exception("INVALID_CREDENTIALS", be);
    }
  }
}


