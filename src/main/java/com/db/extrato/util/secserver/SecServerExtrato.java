package com.db.extrato.util.secserver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(includeFieldNames=true)
public class SecServerExtrato implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private static UserExtratoSecServer uess = null;
	
	private static List<UserExtratoSecServer> users = new ArrayList<UserExtratoSecServer>();
	
	public static UserExtratoSecServer getUserExtrato(String username) {
		
		uess = null;
		
		users.forEach(u -> {
			if (u.getUsername().equals(username)) {
				uess = u;
			}
		});
		
		return uess;
	}

	public static void setUsers(List<UserExtratoSecServer> asList) {
		users.addAll(asList);
	}

	@Cacheable("userperfils")
	public static List<UserExtratoSecServer> getUsers() {
		return users;
	}
}
