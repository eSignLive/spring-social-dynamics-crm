package org.springframework.social.dynamicscrm.connect;

import org.springframework.social.dynamicscrm.api.DynamicsCrm;
import org.springframework.social.dynamicscrm.api.impl.DynamicsCrmTemplate;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2DynamicsCrmTemplate;

/**
 * Created by psmelser on 2015-11-20.
 *
 * @author paul_smelser@silanis.com
 */
public class DynamicsCrmServiceProvider extends AbstractOAuth2ServiceProvider<DynamicsCrm> {

    private String apiUrl;

    public DynamicsCrmServiceProvider(String clientId,
                                      int crmVersion,
                                      String apiUrl,
                                      String url) {
        this(new OAuth2DynamicsCrmTemplate(clientId, crmVersion, url));
        this.apiUrl = apiUrl;
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
