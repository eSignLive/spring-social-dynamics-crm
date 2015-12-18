package org.springframework.social.dynamicscrm.connect.url;

/**
 * Created by psmelser on 2015-11-20.
 *
 * @author paul_smelser@silanis.com
 */
public class DefaultDynamicsUrlProvider implements DynamicsUrlProvider{

    public static final String API_BASE_URL = "https://silanisinc.crm.dynamics.com";
    public static final String OAUTH_BASE_URL = "https://login.windows.net";

    private static final String API_PATH = "/XRMServices/2011/OrganizationData.svc";
    private static final String OAUTH2_AUTHORIZATION_PATH = "/common/oauth2/authorize";
    private static final String OAUTH2_TOKEN_PATH = "/common/oauth2/token";

    public static final String API_URL = API_BASE_URL + API_PATH;

    private final String apiUrl;
    private final String oAuth2AuthorizationUrl;
    private final String oAuth2TokenUrl;

    public DefaultDynamicsUrlProvider() {
        this(API_BASE_URL, OAUTH_BASE_URL);
    }

    public DefaultDynamicsUrlProvider(String alternateApiBaseUrl, String alternateOAuthBaseUrl) {
        apiUrl = alternateApiBaseUrl + API_PATH;
        oAuth2AuthorizationUrl = alternateOAuthBaseUrl + OAUTH2_AUTHORIZATION_PATH;
        oAuth2TokenUrl = alternateOAuthBaseUrl + OAUTH2_TOKEN_PATH;
    }

    @Override
    public String getApiUrl() {
        return apiUrl;
    }

    @Override
    public String getOauth2AuthorizationUrl() {
        return oAuth2AuthorizationUrl;
    }

    @Override
    public String getOauth2TokenUrl() {
        return oAuth2TokenUrl;
    }

}
