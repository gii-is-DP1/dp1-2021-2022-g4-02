package sevenisles.util;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import sevenisles.user.User;

public class ManualLogin {
	
	@Transactional(readOnly = true)
	public static void login (User user) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		UsernamePasswordAuthenticationToken authReq
		 = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), authorities);
		 Authentication newAuth = new 
				 UsernamePasswordAuthenticationToken(authReq.getPrincipal(), authReq.getCredentials(), authReq.getAuthorities());
		SecurityContext sc = SecurityContextHolder.getContext();
		sc.setAuthentication(newAuth);
	}

}
