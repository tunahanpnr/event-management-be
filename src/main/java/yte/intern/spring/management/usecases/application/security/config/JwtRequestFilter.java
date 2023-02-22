package yte.intern.spring.management.usecases.application.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import yte.intern.spring.management.usecases.application.security.CustomUserDetailsManager;
import yte.intern.spring.management.usecases.application.security.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

	@Value(value = "${security.jwt.secret-key}")
	private String secretKey;

	private final CustomUserDetailsManager userDetailsManager;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String authentication = request.getHeader("Authorization");
		if (authentication != null && authentication.startsWith("Bearer")) {
			String jwtToken = authentication.substring(7);
			String username = JwtUtil.extractUsername(jwtToken, secretKey);

			UserDetails userDetails = (UserDetails) userDetailsManager.loadUserByUsername(username);

			if (SecurityContextHolder.getContext().getAuthentication() == null) {
				var token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(token);
			}

		}
		filterChain.doFilter(request, response);

	}
}
