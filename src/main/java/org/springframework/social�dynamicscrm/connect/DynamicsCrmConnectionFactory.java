package org.springframework.social.dynamicscrm.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.dynamicscrm.api.DynamicsCrm;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * Created by psmelser on 2015-11-20.
 *
 * @author paul_smelser@silanis.com
 */
public class DynamicsCrmConnectionFactory extends OAuth2ConnectionFactory<DynamicsCrm> {
    public DynamicsCrmConnectionFactory(String clientId, String organizationId, int crmVersion, String clientSdkVersion){
        this("dynamicscrm"+ crmVersion, new DynamicsCrmServiceProvider(clientId, organizationId, crmVersion, clientSdkVersion), new DynamicsCrmAdapter());
    }

    public DynamicsCrmConnectionFactory(String clientId, String organizationId) {
        this(new DynamicsCrmServiceProvider(clientId, organizationId));
    }
    public DynamicsCrmConnectionFactory(String providerId, OAuth2ServiceProvider<DynamicsCrm> serviceProvider, ApiAdapter<DynamicsCrm> apiAdapter) {
        super(providerId, serviceProvider, apiAdapter);
    }

    private DynamicsCrmConnectionFactory(OAuth2ServiceProvider<DynamicsCrm> serviceProvider){
        this("dynamics2011", serviceProvider, new DynamicsCrmAdapter());
    }
}
