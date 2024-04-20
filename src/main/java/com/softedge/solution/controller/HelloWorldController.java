package com.softedge.solution.controller;

import com.softedge.solution.common.JwtAuthenticationConfig;
import com.softedge.solution.models.AuthenticationRequest;
import com.softedge.solution.models.AuthenticationResponse;
import com.softedge.solution.models.Permission;
import com.softedge.solution.models.UserRegistration;
import com.softedge.solution.service.MyUserDetailsService;
import com.softedge.solution.service.PermissionService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
class HelloWorldController {

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	@Autowired
    JdbcUserDetailsManager jdbcUserDetailsManager;

	@Autowired
	private JwtAuthenticationConfig config;

	@Autowired
	private AuthenticationManager authenticationManager;


	@Autowired
	private MyUserDetailsService userDetailsService;

	@Autowired
	private PermissionService permissionService;

	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {


		try{
			Authentication auth = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
			Instant now = Instant.now();
			String token = Jwts.builder()
					.setSubject(auth.getName())
					.claim("authorities", auth.getAuthorities().stream()
							.map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
					.setIssuedAt(Date.from(now))
					.setExpiration(Date.from(now.plusSeconds(config.getExpiration())))
					.signWith(SignatureAlgorithm
							.HS256, config.getSecret().getBytes())
					.compact();
			return ResponseEntity.ok(new AuthenticationResponse(token));

		}
		catch (BadCredentialsException exception){
			throw new Exception("Incorrect username or password", exception);
		}

	}

	@PostMapping(value = "/register")
	public String processRegister(@RequestBody UserRegistration userRegistrationObject) {

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		userRegistrationObject.getAuthorities().forEach(authority -> {
			authorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
			});

		String encodedPassword = bCryptPasswordEncoder.encode(userRegistrationObject.getPassword());

		User user = new User(userRegistrationObject.getUsername(), encodedPassword, authorities);


		jdbcUserDetailsManager.createUser(user);

		UserRegistration savedUser = userDetailsService.getUserByUsername(user.getUsername());

		System.out.println(savedUser.getUsername()+" "+savedUser.getId());

		/*Permission permission = new Permission();
		permission.setPermissionName(userRegistrationObject.getPermission().getPermissionName());
		permission.setUserRegistration(savedUser);

		permissionService.savePermission(permission);*/


		return user.getUsername()+" is successfully registered";
	}

}
