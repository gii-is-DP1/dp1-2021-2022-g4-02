package sevenisles.auditory;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {
	@Override
	public Optional<String> getCurrentAuditor(){
		Optional<String> currentUserName = Optional.empty();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(!(auth instanceof AnonymousAuthenticationToken)) {
			currentUserName = Optional.of(auth.getName());
		}else if(auth != null){
			currentUserName = Optional.of(auth.getPrincipal().toString());
		}
		return currentUserName;
	}

}
