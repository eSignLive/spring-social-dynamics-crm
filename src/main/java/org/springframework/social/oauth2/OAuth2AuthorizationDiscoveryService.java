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
    public static final String DISCOVERY_SERVICE_PATH = "XRMServices/2011/Organization.svc/web?SdkClientVersion=";
    public RestTemplate template;

    public OAuth2AuthorizationDiscoveryService() {
        template = new RestTemplate();
        template.setErrorHandler(new DynamicsCrmRestTemplateErrorHandler());
    }

    public OAuth2Endpoint exchangeForAuthorizeEndpoint(String url, String clientSdkVersion) {
        String targetUrl = buildDiscoveryUri(url, clientSdkVersion);
        ResponseEntity<String> entity = template.getForEntity(targetUrl, String.class);
        String strings = entity.getHeaders().get("WWW-Authenticate").get(0);
        return OAuth2Endpoint.parseAuthUrl(strings, url);
    }

    private String buildDiscoveryUri(String url, String clientSdkVersion){
        return new StringBuilder(url)
                .append(DISCOVERY_SERVICE_PATH)
                .append(clientSdkVersion)
                .toString();
    }
}
