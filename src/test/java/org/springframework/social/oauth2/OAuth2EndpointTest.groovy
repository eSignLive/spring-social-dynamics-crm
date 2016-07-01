package org.springframework.social.oauth2

import spock.lang.Specification
import static groovy.util.GroovyTestCase.assertEquals

/**
 * Created by psmelser on 16-06-29.
 * @author paul.smelser@esignlive.com
 */
class OAuth2EndpointTest extends Specification {
    private final String wwwAuthenticateHeader = "Bearer authorization_uri=https://login.windows.net/2eccf943-33fb-4893-8f20-3e73cf4414c4/oauth2/authorize";

    def "when authorize url is parsed from discovery service the correct url is returned"() {
        when:
            def authUrl = OAuth2Endpoint.parseAuthUrl(wwwAuthenticateHeader).getAuthUrl()
        then:
            assertEquals("https://login.windows.net/2eccf943-33fb-4893-8f20-3e73cf4414c4/oauth2/authorize", authUrl);
    }

    def "when parsing the tenant Id from the url the correct value is retrieved"() {
        when:
            def tenantId = OAuth2Endpoint.parseAuthUrl(wwwAuthenticateHeader).parseTenantId()
        then:
            assertEquals("2eccf943-33fb-4893-8f20-3e73cf4414c4", tenantId)
    }

    def "when the token uri is parsed from the discovery service the correct url is returned"() {
        when:
            def tokenUrl = OAuth2Endpoint.parseAuthUrl(wwwAuthenticateHeader).parseTokenUri()
        then:
            assertEquals("https://login.windows.net/2eccf943-33fb-4893-8f20-3e73cf4414c4/oauth2/token", tokenUrl)
    }
}
