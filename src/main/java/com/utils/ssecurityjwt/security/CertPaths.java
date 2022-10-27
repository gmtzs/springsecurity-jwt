package com.utils.ssecurityjwt.security;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ConfigurationProperties("rsa")
public class CertPaths {
	
	private RSAPublicKey  publicKey;
	private RSAPrivateKey privateKey;

}
