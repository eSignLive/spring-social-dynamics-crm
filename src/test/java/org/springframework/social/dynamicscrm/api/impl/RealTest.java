package org.springframework.social.dynamicscrm.api.impl;

import com.google.common.base.Charsets;
import org.junit.Test;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.dynamicscrm.api.DynamicsCrm;
import org.springframework.social.dynamicscrm.connect.DynamicsCrmConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;

import java.io.UnsupportedEncodingException;

import static java.net.URLEncoder.encode;
import static org.apache.commons.lang3.StringUtils.defaultIfBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Created by psmelser on 2015-12-14.
 *
 * @author paul_smelser@silanis.com
 */
public class RealTest {

    public static final String UTF_8 = Charsets.UTF_8.toString();

    @Test
    public void test() throws UnsupportedEncodingException {
        String clientId = "";
        String clientSecret= "";
        String redirectUrl = "";
        String endpoint = "";

        findConnectionFactory(clientId, clientSecret);
        DynamicsCrmConnectionFactory connectionFactory = new DynamicsCrmConnectionFactory(clientId, clientSecret);
        OAuth2Operations oAuthOperations = connectionFactory.getOAuthOperations();

        OAuth2Parameters parameters = new OAuth2Parameters();

        final String oAuthCallbackPath = buildOAuthCallbackPath(DynamicsCrm.PROVIDER_NAME);
        parameters.set("callback_uri", oAuthCallbackPath);
        parameters.set("redirect_uri", oAuthCallbackPath);

        if (isNotBlank(endpoint)) {
            parameters.set("endpoint", encode(endpoint, UTF_8));
        }

        final String oAuthRedirectUrl = defaultIfBlank(redirectUrl, endpoint);
        if (isNotBlank(oAuthRedirectUrl)) {
            parameters.set("state", encode(oAuthRedirectUrl, UTF_8));
        }

        String authorizeUrl = oAuthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, parameters);

//        connectionFactory.createConnection(new ConnectionData(DynamicsCrm.PROVIDER_NAME));

        AccessGrant grant = getAccessGrant(DynamicsCrm.PROVIDER_NAME, parameters.getState(), connectionFactory);

        Connection connection = connectionFactory.createConnection(grant);
    }

    private OAuth2ConnectionFactory findConnectionFactory(String clientId, String clientSecret) {
        DynamicsCrmConnectionFactory connectionFactory = new DynamicsCrmConnectionFactory(clientId, clientSecret);
        if (connectionFactory instanceof OAuth2ConnectionFactory) {
            return connectionFactory;
        } else {
            throw new RuntimeException();
        }
    }

    private String buildOAuthCallbackPath(String providerId){
        return "http://localhost:8888/oauth/"+ providerId;
    }

    protected AccessGrant getAccessGrant(String providerId, String code, OAuth2ConnectionFactory connectionFactory) {
        final OAuth2Operations ops = connectionFactory.getOAuthOperations();
        return getAccessGrant(providerId, code, (OAuth2ConnectionFactory) ops);
    }


}
