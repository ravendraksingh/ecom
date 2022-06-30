package com.rks.oauthserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.keys.KeyManager;
import org.springframework.security.crypto.keys.StaticKeyGeneratingKeyManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import java.time.Duration;

@Configuration
@EnableWebSecurity
@Import(OAuth2AuthorizationServerConfiguration.class)
public class DefaultSecurityConfig extends WebSecurityConfigurerAdapter {

    Logger logger = LoggerFactory.getLogger(DefaultSecurityConfig.class);

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetailsManager uds = new InMemoryUserDetailsManager();
        UserDetails u1 = User.withUsername("admin").password("admin").authorities("read").authorities("write").build();
        uds.createUser(u1);
        return uds;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
    @Bean
    RegisteredClientRepository registeredClientRepository() {
        logger.info("### in DefaultSecurityConfig:registeredClientRepository ###");
        RegisteredClient rc = RegisteredClient.withId("client").clientId("client1").clientSecret("secret1")
                .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://localhost:9000/authorized")
                .tokenSettings(t -> {
                   t.enableRefreshTokens(true);
                   t.reuseRefreshTokens(true);
                   t.accessTokenTimeToLive(Duration.ofHours(5));
                }).scope("read").scope("write").build();
        logger.info(rc.toString());
        return new InMemoryRegisteredClientRepository(rc);


    }
    @Bean
    KeyManager keyManager() {
        return new StaticKeyGeneratingKeyManager();
    }

}
