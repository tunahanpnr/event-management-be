package yte.intern.spring.management.usecases.application.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import yte.intern.spring.management.usecases.application.entity.Users;
import yte.intern.spring.management.usecases.application.repository.UsersRepository;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsManager  {

	private final UsersRepository userRepository;
	private final PasswordEncoder passwordEncoder;


	public void changePassword(final String oldPassword, final String newPassword) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = (String) authentication.getPrincipal();
		Users user = (Users) loadUserByUsername(username);
		if(passwordEncoder.matches(oldPassword, user.getPassword())) {
			user.setPassword(passwordEncoder.encode(newPassword));
			userRepository.save(user);
		} else {
			throw new BadCredentialsException("Wrong old password is given!");
		}
	}

	public boolean userExists(final String username) {
		return userRepository.existsByUsername(username);
	}

	public UserDetails loadUserByUsername(final String username) {
		Users user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}

		List<GrantedAuthority> authorities
				= new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(user.getRole().name()));

		return new org
				.springframework
				.security
				.core
				.userdetails
				.User(user.getUsername(), user.getPassword(), authorities);

	}
}
