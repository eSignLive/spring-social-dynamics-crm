package org.springframework.social.oauth2

import spock.lang.Specification
import static groovy.util.GroovyTestCase.assertEquals

/**
 * Created by psmelser on 16-06-29.
 * @author paul.smelser@esignlive.com
 */
class OAuth2EndpointTest extends Specification {
    private static final String EXPECTED_AUTHORIZE_URI = "https://login.windows.net/2eccf943-33fb-4893-8f20-3e73cf4414c4/oauth2/authorize"
    private static final String EXPECTED_TOKEN_URI = "https://login.windows.net/2eccf943-33fb-4893-8f20-3e73cf4414c4/oauth2/token"
    private static final String EXPECTED_RESOURCE_ID = "https://silanisinc.crm.dynamics.com/"
    private static final String DEFAULT_RESOURCE_ID = "https://silanisinc.crm3.dynamics.com/"
    private final String wwwAuthenticateHeader = "Bearer authorization_uri=https://login.windows.net/2eccf943-33fb-4893-8f20-3e73cf4414c4/oauth2/authorize";
    private final String wwwAuthenticateHeaderWithResourceId = "Bearer authorization_uri=https://login.windows.net/2eccf943-33fb-4893-8f20-3e73cf4414c4/oauth2/authorize, resource_id=https://silanisinc.crm.dynamics.com/";

    def "when authorize url is parsed from discovery service the correct url is returned"() {
        when:
            def authUrl = OAuth2Endpoint.parseAuthUrl(wwwAuthenticateHeader, DEFAULT_RESOURCE_ID).getAuthUrl()
        then:
            assertEquals(EXPECTED_AUTHORIZE_URI, authUrl);
    }

    def "when parsing the tenant Id from the url the correct value is retrieved"() {
        when:
            def tenantId = OAuth2Endpoint.parseAuthUrl(wwwAuthenticateHeader, DEFAULT_RESOURCE_ID).parseTenantId()
        then:
            assertEquals("2eccf943-33fb-4893-8f20-3e73cf4414c4", tenantId)
    }

    def "when the token uri is parsed from the discovery service the correct url is returned"() {
        when:
            def tokenUrl = OAuth2Endpoint.parseAuthUrl(wwwAuthenticateHeader, DEFAULT_RESOURCE_ID).parseTokenUri()
        then:
            assertEquals(EXPECTED_TOKEN_URI, tokenUrl)
    }
    def "When the token has resource_id, the resource Id and authorize url are parsed correctly"(){
        when:
            def endpoint = OAuth2Endpoint.parseAuthUrl(wwwAuthenticateHeader, DEFAULT_RESOURCE_ID);
        then:
            assertEquals(EXPECTED_AUTHORIZE_URI, endpoint.getAuthUrl());
            assertEquals(EXPECTED_TOKEN_URI, endpoint.parseTokenUri());
            assertEquals(DEFAULT_RESOURCE_ID, endpoint.getResourceId());

    }
    def "When default resourceId is passed the urls are parsed correctly"(){
        when:
        def endpoint = OAuth2Endpoint.parseAuthUrl(wwwAuthenticateHeaderWithResourceId, DEFAULT_RESOURCE_ID);
        then:
        assertEquals(EXPECTED_AUTHORIZE_URI, endpoint.getAuthUrl());
        assertEquals(EXPECTED_TOKEN_URI, endpoint.parseTokenUri());
        assertEquals(EXPECTED_RESOURCE_ID, endpoint.getResourceId());
    }
}
