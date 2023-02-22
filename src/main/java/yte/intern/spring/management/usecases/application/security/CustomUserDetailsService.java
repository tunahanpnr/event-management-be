package yte.intern.spring.management.usecases.application.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import yte.intern.spring.management.usecases.application.repository.UsersRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final CustomUserDetailsManager userDetailsManager;


	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		return userDetailsManager.loadUserByUsername(username);
	}
}
