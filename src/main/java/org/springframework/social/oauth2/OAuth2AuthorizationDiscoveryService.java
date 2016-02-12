package org.springframework.social.oauth2;

import org.springframework.http.ResponseEntity;
import org.springframework.social.dynamicscrm.rest.errorhandling.DynamicsCrmRestTemplateErrorHandler;
import org.springframework.web.client.RestTemplate;

/**
 * Created by psmelser on 2015-12-18.
 *
 * @author paul_smelser@silanis.com
 */
public class OAuth2AuthorizationDiscoveryService {
    public static final String PROTOCOL = "https://";
    public static final String XRM_SERVICES_PATH = ".crm.dynamics.com/XRMServices/";
    public static final String ORGANIZATION_PATH = "/Organization.svc/web?SdkClientVersion=";
    public RestTemplate template;

    public OAuth2AuthorizationDiscoveryService() {
        template = new RestTemplate();
        template.setErrorHandler(new DynamicsCrmRestTemplateErrorHandler());
    }

    public OAuth2Endpoints exchangeForAuthorizeEndpoint(String organizationId, int crmVersion, String clientSdkVersion) {
        String targetUrl = buildDiscoveryUri(organizationId, crmVersion, clientSdkVersion);
        ResponseEntity<String> entity = template.getForEntity(targetUrl, String.class);
        String strings = entity.getHeaders().get("WWW-Authenticate").get(0);
        return OAuth2Endpoints.parseAuthUrl(strings);
    }

    private String buildDiscoveryUri(String organizationId, int crmVersion, String clientSdkVersion){
        return new StringBuilder(PROTOCOL)
                .append(organizationId)
                .append(XRM_SERVICES_PATH)
                .append(crmVersion)
                .append(ORGANIZATION_PATH)
                .append(clientSdkVersion).toString();
    }
}
