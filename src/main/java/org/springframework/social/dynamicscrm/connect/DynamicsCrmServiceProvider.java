package org.springframework.social.dynamicscrm.connect;

import org.springframework.social.dynamicscrm.api.DynamicsCrm;
import org.springframework.social.dynamicscrm.api.impl.DynamicsCrmTemplate;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2DynamicsCrmTemplate;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * Created by psmelser on 2015-11-20.
 *
 * @author paul_smelser@silanis.com
 */
public class DynamicsCrmServiceProvider extends AbstractOAuth2ServiceProvider<DynamicsCrm> {

    public static final String PROTOCOL = "https://";
    public static final String XRM_SERVICES_PATH = ".crm.dynamics.com/XRMServices/";
    public static final String ORGANIZATION_SERVICE_PATH = "/Organization.svc";
    private String apiUrl;

    public DynamicsCrmServiceProvider(String clientId, String organizationId) {
        this(clientId, organizationId, 2011, "6.1.0.533");
    }

    public DynamicsCrmServiceProvider(String clientId, String organizationId, int crmVersion, String clientSdkVersion) {
        this(new OAuth2DynamicsCrmTemplate(clientId, organizationId, crmVersion, clientSdkVersion));
        apiUrl = buildApiUrl(organizationId, 2011);
    }

    private DynamicsCrmServiceProvider(OAuth2DynamicsCrmTemplate template){
        super(template);
        ((OAuth2Template)getOAuthOperations()).setUseParametersForClientAuthentication(true);
    }

    private String buildApiUrl(String organizationId, int crmVersion){
        return new StringBuilder(PROTOCOL)
                .append(organizationId)
                .append(XRM_SERVICES_PATH)
                .append(crmVersion)
                .append(ORGANIZATION_SERVICE_PATH)
                .toString();
    }

    @Override
    public DynamicsCrm getApi(String accessToken) {
        return new DynamicsCrmTemplate(accessToken, apiUrl);
    }
}
