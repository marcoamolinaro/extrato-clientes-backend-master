package com.db.extrato.security.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.db.extrato.util.secserver.SecServerExtrato;
import com.db.extrato.util.secserver.UserExtratoSecServer;

@Component
public class JwtUserDetailsService implements UserDetailsService {
	
  public UserDetails loadUserByUsername(String username) {
    //TODO: Aqui eu deveria trazer o usuário do cache salvo depois de se comunicar com o SecServer
	  	
	 UserExtratoSecServer uess = SecServerExtrato.getUserExtrato(username); 

	return uess;
	
	//return new User(user.getUsername(), new BCryptPasswordEncoder().encode(user.getUsername()), List.of(new SimpleGrantedAuthority(user.getUsername())));
  }
}
