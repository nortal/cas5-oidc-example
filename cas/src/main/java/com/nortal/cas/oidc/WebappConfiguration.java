package com.nortal.cas.oidc;

import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.support.oauth.validator.OAuth20Validator;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebappConfiguration {
	
    @Autowired
    @Qualifier("servicesManager")
    private ServicesManager servicesManager;
    
    @Autowired
    @Qualifier("oAuthValidator")
    private OAuth20Validator oAuthValidator;

    @Bean
    @RefreshScope
    public Authenticator<UsernamePasswordCredentials> oAuthClientAuthenticator() {
        return new OAuthClientPropsAuthenticator(oAuthValidator, servicesManager);
    }
}
