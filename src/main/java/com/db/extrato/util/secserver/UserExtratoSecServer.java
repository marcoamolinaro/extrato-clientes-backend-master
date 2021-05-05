package com.db.extrato.util.secserver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(includeFieldNames=true)
public class UserExtratoSecServer implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String username;
	private String password;
	private String confirm_password;
	private List<PerfilAcessoSecServer> perfis = new ArrayList<PerfilAcessoSecServer>();
	
	public UserExtratoSecServer(Long id, String username, String password, String confirm_password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.confirm_password = confirm_password;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
