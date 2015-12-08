package org.springframework.social.dynamicscrm.connect;

import org.springframework.social.dynamicscrm.api.DynamicsCrm;
import org.springframework.social.dynamicscrm.api.impl.DynamicsCrmTemplate;
import org.springframework.social.dynamicscrm.connect.url.DefaultDynamicsUrlProvider;
import org.springframework.social.dynamicscrm.connect.url.DynamicsUrlProvider;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * Created by psmelser on 2015-11-20.
 *
 * @author paul_smelser@silanis.com
 */
public class DynamicsCrmServiceProvider extends AbstractOAuth2ServiceProvider<DynamicsCrm> {

    private final String apiUrl;
    private final String uploadUrl;

    public DynamicsCrmServiceProvider(String clientId, String clientSecret) {
        this(clientId, clientSecret, new DefaultDynamicsUrlProvider());
    }

    public DynamicsCrmServiceProvider(String clientId, String clientSecret, String alternateApiBaseUrl, String alternateOAuthBaseUrl, String alternateUploadBaseUrl) {
        this(clientId, clientSecret, new DefaultDynamicsUrlProvider(alternateApiBaseUrl, alternateOAuthBaseUrl, alternateUploadBaseUrl));
    }

    private DynamicsCrmServiceProvider(String clientId, String clientSecret, DynamicsUrlProvider dynamicsUrlProvider) {
        super(new OAuth2Template(clientId, clientSecret, dynamicsUrlProvider.getOauth2AuthorizationUrl(), dynamicsUrlProvider.getOauth2TokenUrl()));
        apiUrl = dynamicsUrlProvider.getApiUrl();
        uploadUrl = dynamicsUrlProvider.getUploadUrl();
        ((OAuth2Template)getOAuthOperations()).setUseParametersForClientAuthentication(true);
    }
    @Override
    public DynamicsCrm getApi(String accessToken) {
        return new DynamicsCrmTemplate(accessToken, apiUrl, uploadUrl);
    }
}
