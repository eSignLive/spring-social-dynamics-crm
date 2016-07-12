package org.springframework.social.dynamicscrm.connect;

import org.springframework.social.dynamicscrm.api.DynamicsCrm;
import org.springframework.social.dynamicscrm.api.impl.DynamicsCrmTemplate;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2DynamicsCrmTemplate;

import java.util.UUID;

/**
 * Created by psmelser on 2015-11-20.
 *
 * @author paul_smelser@silanis.com
 */
public class DynamicsCrmServiceProvider extends AbstractOAuth2ServiceProvider<DynamicsCrm> {

    private String apiUrl;

    public DynamicsCrmServiceProvider(String clientId,
                                      String apiUrl,
                                      String url,
                                      String clientSdkVersion) {
        this(new OAuth2DynamicsCrmTemplate(clientId, url, clientSdkVersion));
        this.apiUrl = ensureTrailingSlashInBaseUrl(apiUrl);
    }

    private String ensureTrailingSlashInBaseUrl(String apiUrl) {
        if(!apiUrl.endsWith("/")){ apiUrl = apiUrl + "/"; }
        return apiUrl;
    }

    private DynamicsCrmServiceProvider(OAuth2DynamicsCrmTemplate template){
        super(template);
        ((OAuth2DynamicsCrmTemplate)getOAuthOperations()).setUseParametersForClientAuthentication(true);
    }

    @Override
    public DynamicsCrm getApi(String accessToken) {
        return new DynamicsCrmTemplate(accessToken, apiUrl);
    }
}
