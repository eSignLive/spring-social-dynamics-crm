package org.springframework.social.oauth2
import spock.lang.Specification

import static groovy.util.GroovyTestCase.assertEquals
/**
 * Created by psmelser on 16-06-23.
 * @author paul.smelser@esignlive.com
 */
class OAuth2DynamicsCrmTemplateTest extends Specification {

    def "when auth url is built parameters are added correctly"() {
        given:
            String clientId = UUID.randomUUID().toString();
            def template = new OAuth2DynamicsCrmTemplate(clientId, "https://org.crm.dynamics.com", "6.0.0");
        when:
            def oauth2Params = new OAuth2Parameters();

            def url = template.buildAuthUrl("http://localhost/authorize", GrantType.AUTHORIZATION_CODE, oauth2Params);
        then:
            assertEquals(String.format("http://localhost/authorize?client_id=%s&response_type=code", clientId), url)
    }

}
