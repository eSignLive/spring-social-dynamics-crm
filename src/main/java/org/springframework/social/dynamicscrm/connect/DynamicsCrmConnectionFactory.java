package org.springframework.social.dynamicscrm.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.dynamicscrm.api.DynamicsCrm;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

import java.util.UUID;

/**
 * Created by psmelser on 2015-11-20.
 *
 * @author paul_smelser@silanis.com
 */
public class DynamicsCrmConnectionFactory extends OAuth2ConnectionFactory<DynamicsCrm> {
    public DynamicsCrmConnectionFactory(UUID clientId, String apiUrl, String url, String clientSdkVersion){
        this("dynamicscrm", new DynamicsCrmServiceProvider(clientId, apiUrl, url, clientSdkVersion), new DynamicsCrmAdapter());
    }


    public DynamicsCrmConnectionFactory(String providerId, OAuth2ServiceProvider<DynamicsCrm> serviceProvider, ApiAdapter<DynamicsCrm> apiAdapter) {
        super(providerId, serviceProvider, apiAdapter);
    }

    private DynamicsCrmConnectionFactory(OAuth2ServiceProvider<DynamicsCrm> serviceProvider){
        this("dynamicscrm", serviceProvider, new DynamicsCrmAdapter());
    }

    protected String extractProviderUserId(AccessGrant accessGrant) {
        return accessGrant.getScope();
    }
}
