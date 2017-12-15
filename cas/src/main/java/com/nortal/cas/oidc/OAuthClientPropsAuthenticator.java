package com.nortal.cas.oidc;

import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.support.oauth.authenticator.OAuthClientAuthenticator;
import org.apereo.cas.support.oauth.profile.OAuthClientProfile;
import org.apereo.cas.support.oauth.services.OAuthRegisteredService;
import org.apereo.cas.support.oauth.util.OAuth20Utils;
import org.apereo.cas.support.oauth.validator.OAuth20Validator;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.exception.CredentialsException;

public class OAuthClientPropsAuthenticator extends OAuthClientAuthenticator {

	private ServicesManager servicesManager;

	public OAuthClientPropsAuthenticator(OAuth20Validator validator, ServicesManager servicesManager) {
		super(validator, servicesManager);
		this.servicesManager = servicesManager;
	}
	
	@Override
	public void validate(UsernamePasswordCredentials credentials, WebContext context) throws CredentialsException {
		super.validate(credentials, context);
		OAuthClientProfile userProfile = (OAuthClientProfile) credentials.getUserProfile();
		final OAuthRegisteredService registeredService = OAuth20Utils.getRegisteredOAuthService(this.servicesManager, userProfile.getId());
		registeredService.getProperties().forEach((key, prop) -> userProfile.addAttribute(key, prop.getValue()));
	}

}
