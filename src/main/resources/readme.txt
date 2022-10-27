1 Create a spring starter project adding Spring web , oauth2 resource server, and spring configuration processor dependencies.
this will generate the next dependencies in the pom.xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>

2 Create REST Controller

3 Add spring security configuration 
 
 Create a Security Filter Chain bean
 disabling csrf
 authorizingrequest
 sessionmagement stateless
 httpbasic with defaults
 build
 
 4 Create a UserDetailsManager bean
 
 
 5 Add .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt) to the SecurityFilterChain 
 
 6 Create pulic and private key
 # create rsa key pair
openssl genrsa -out keypair.pem 2048

# extract public key
openssl rsa -in keypair.pem -pubout -out public.pem

# create private key in PKCS#8 format
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem

6 Create Java Record o properties wired 

 @ConfigurationProperties(prefix = "rsa")
public record RsaKeyProperties(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
}
 
 properties
 rsa.private-key=classpath:certs/private.pem
rsa.public-key=classpath:certs/public.pem

7 Add security paths to config 
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final RsaKeyProperties rsaKeys;

    public SecurityConfig(RsaKeyProperties rsaKeys) {
        this.rsaKeys = rsaKeys;
    }
    
    
8  Add JWT Decoder Bean 

@Bean
JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
}


9 Add JWT Encoder Bean and REST Auth Controller


