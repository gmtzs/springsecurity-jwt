package com.utils.ssecurityjwt.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
public class SecurityConfigurator {

	
	private final CertPaths certPaths;
	
	@Bean
	JwtDecoder jwtDecoder() {
	    return NimbusJwtDecoder.withPublicKey(certPaths.getPublicKey()).build();
	}
	
	@Bean
	JwtEncoder jwtEncoder() {
	    JWK jwk = new RSAKey.Builder(certPaths.getPublicKey()).privateKey(certPaths.getPrivateKey()).build();
	    JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
	    return new NimbusJwtEncoder(jwks);
	}
	
	public SecurityConfigurator(CertPaths certPaths) {
		super();
		this.certPaths = certPaths;
	}

	@Bean
	public InMemoryUserDetailsManager getInMemoryUserDetailsManager() {
		
		UserDetails user = User.withUsername("user")
				.password("{noop}password")
				.roles("USER")
				.build();
		UserDetails admin = User.withUsername("admin")
				.password("{noop}password")
				.roles("USER")
				.build();
		InMemoryUserDetailsManager iudm= new InMemoryUserDetailsManager(user,admin);
		
		return iudm;
				
		
	}
	
/**
 * This bean :
1 Disable Cross-Site Request Forgery (CSRF)
2 The user should be authenticated for any request in the application.
3 Spring Security will never create an HttpSession and it will never use it to obtain the Security Context.
4 Spring Security’s HTTP Basic Authentication support is enabled by default. However, as soon as any servlet-based 
configuration is provided, HTTP Basic must be explicitly provided.	
5 The OAuth2ResourceServerConfigurer is an AbstractHttpConfigurer for OAuth 2.0 Resource Server Support. By default,
 this wires a BearerTokenAuthenticationFilter, which can be used to parse the request for bearer tokens and make an authentication attempt.
 
 * @param http
 * @return
 * @throws Exception
 */
	@Bean
	public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
		SecurityFilterChain fch= http
				.csrf(csrf-> csrf.disable())  //1
				.authorizeRequests(ar-> ar    // 2
						.antMatchers("/user").hasRole("USER")
						.antMatchers("/admin").hasRole("ADMIN")
						.anyRequest().authenticated())
				.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)  //5 
				.sessionManagement( sm ->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  //3
				.httpBasic(Customizer.withDefaults())   //4
				.build();
		return fch;
	}
	
}
