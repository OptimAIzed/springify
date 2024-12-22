package com.code_assistant.user_service.config.Authentication;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Builder
@Getter
public class CustomUserDetails implements UserDetails {
	private Long id;
    private  String email;
    private  String password;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// Return authorities based on role
//		System.out.println("this is role: " + role + " of user : " + email);
        return Collections.emptyList();
	}

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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