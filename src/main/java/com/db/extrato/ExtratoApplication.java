package com.db.extrato;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.db.extrato.util.secserver.PerfilAcessoSecServer;
import com.db.extrato.util.secserver.SecServerExtrato;
import com.db.extrato.util.secserver.UserExtratoSecServer;

import lombok.extern.java.Log;

@Log
@EnableCaching
@SpringBootApplication
public class ExtratoApplication implements CommandLineRunner {
	

	@Value("${use.secserver}")
	private String secserver;
	
	@Value("${use.users}")
	private String numberOfUsers;

	@Value("${use.password}")
	private String password;
	
	@Value("${use.listOfUsers}")
	private List<String> listOfUsers;
	
	@Value("${use.perfils}")
	private List<String> listOPerfils;
	
	@Value("${use.users.perfils}")
	private List<String> listOfUsersPerfils;
	
	private List<UserExtratoSecServer> listOfUess = new ArrayList<UserExtratoSecServer>();
	private List<PerfilAcessoSecServer> listOfPass = new ArrayList<PerfilAcessoSecServer>();
	
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder () {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(ExtratoApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {	
		
		if (secserver.equals("N")) {
			
			Integer numOfUsers = Integer.parseInt(numberOfUsers);
			
			log.info("NÚMERO DE USUÁRIOS ["+numOfUsers+"]");
			log.info("PASSWORD [" + password + "]");
			log.info("LIST OF USERS [" + listOfUsers.toString() + "]");
			log.info("LIST OF PERFIS [" + listOPerfils + "]");
			log.info("LIST OF USERS PERFILS " + listOfUsersPerfils + "]");
			
			for (int i = 0; i < numOfUsers; i++) {
				UserExtratoSecServer u = new UserExtratoSecServer();
				u.setId((long)i+1);
				String username = listOfUsers.get(i);
				u.setUsername(username);
				u.setPassword(password);
				u.setConfirm_password("");
				listOfUess.add(u);
				log.info("Usuário [ " + u.getUsername() + "]" );
			}
			
			for (int i = 0; i < listOPerfils.size(); i++) {
				PerfilAcessoSecServer p = new PerfilAcessoSecServer();
				p.setId((long)i+1);
				p.setPerfilAcesso(listOPerfils.get(i));
				listOfPass.add(p);
				log.info("Perfil [" + p.getPerfilAcesso() + "]");
			}
			
			for (int i=0; i < listOfUess.size(); i++) {
				UserExtratoSecServer u = listOfUess.get(i);
				String strPerfil = listOfUsersPerfils.get(i);
				List<String> perfils = new ArrayList<String>();
				perfils.addAll(Arrays.asList(strPerfil.split(";")));
				log.info("Perfils size [" + perfils.size() + "]");
				List<PerfilAcessoSecServer> lpass = new ArrayList<PerfilAcessoSecServer>();
				perfils.forEach(p -> {
					listOfPass.forEach(pass -> {
						if (pass.getPerfilAcesso().equals(p)) {
							PerfilAcessoSecServer pa = pass;
							lpass.add(pa);
						}
					});
				});
				u.setPerfis(lpass);
			}
			
			
			log.info("Size of listOfUsersPerfils [" + listOfUsersPerfils.size() + "]");
			
			SecServerExtrato.setUsers(listOfUess);
			SecServerExtrato.getUsers().forEach(u -> {
				log.info(u.toString());
			});
			
		}
		
	}	
}
