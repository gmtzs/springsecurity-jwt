 package com.utils.ssecurityjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.utils.ssecurityjwt.security.CertPaths;

@SpringBootApplication
@EnableConfigurationProperties(CertPaths.class)
public class SssecurityJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SssecurityJwtApplication.class, args);
	}

}
