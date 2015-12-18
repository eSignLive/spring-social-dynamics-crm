package org.springframework.social.oauth2;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.social.dynamicscrm.rest.AuthorizeEndpoint;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.social.support.FormMapHttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by psmelser on 2015-12-18.
 *
 * @author paul_smelser@silanis.com
 */
public class OAuth2DynamicsCrmTemplate implements OAuth2Operations {

    private final String clientId;
    private final String organizationId;
    private final String clientSdkVersion;
    private final int crmVersion;
    private boolean useParametersForClientAuthentication;
    private RestTemplate resource;

    public OAuth2DynamicsCrmTemplate(String clientId, String organizationId, int crmVersion, String clientSdkVersion) {
        this.crmVersion = crmVersion;
        Assert.notNull(clientId, "The clientId property cannot be null");
        Assert.notNull(organizationId, "The organizationId property cannot be null");
        Assert.notNull(clientSdkVersion, "The clientSdkVersion property cannot be null");
        this.clientId = clientId;
        this.organizationId = organizationId;
        this.clientSdkVersion = clientSdkVersion;
        resource = createRestTemplate();
    }

    public void setUseParametersForClientAuthentication(boolean useParametersForClientAuthentication) {
        this.useParametersForClientAuthentication = useParametersForClientAuthentication;
    }

    @Override
    public String buildAuthorizeUrl(OAuth2Parameters oAuth2Parameters) {
        return buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, oAuth2Parameters);
    }

    @Override
    public String buildAuthorizeUrl(GrantType grantType, OAuth2Parameters oAuth2Parameters) {
        OAuth2AuthorizeDiscoveryService discoveryService = new OAuth2AuthorizeDiscoveryService();
        AuthorizeEndpoint endpoint = discoveryService.exchangeForAuthorizeUri(organizationId, crmVersion, clientSdkVersion);
        return buildAuthUrl(endpoint.getAuthUrl(), grantType, oAuth2Parameters);
    }

    @Override
    public String buildAuthenticateUrl(OAuth2Parameters oAuth2Parameters) {
        return buildAuthorizeUrl(oAuth2Parameters);
    }

    @Override
    public String buildAuthenticateUrl(GrantType grantType, OAuth2Parameters oAuth2Parameters) {
        return buildAuthorizeUrl(grantType, oAuth2Parameters);
    }

    @Override
    public AccessGrant exchangeForAccess(String authorizationCode, String redirectUri, MultiValueMap<String, String> multiValueMap) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.set("client_id", clientId);
        params.set("code", authorizationCode);
        params.set("redirect_uri", redirectUri);
        params.set("grant_type", "authorization_code");
        if (multiValueMap != null) {
            params.putAll(multiValueMap);
        }
        return postForAccessGrant(exchangeForAccessTokenUri(), params);
    }

    @Override
    public AccessGrant exchangeCredentialsForAccess(String username, String password, MultiValueMap<String, String> multiValueMap) {
        LinkedMultiValueMap params = new LinkedMultiValueMap();
        if(this.useParametersForClientAuthentication) {
            params.set("client_id", this.clientId);
        }
        params.set("username", username);
        params.set("password", password);
        params.set("grant_type", "password");
        if(multiValueMap != null) {
            params.putAll(multiValueMap);
        }

        return this.postForAccessGrant(exchangeForAccessTokenUri(), params);
    }

    @Override
    public AccessGrant refreshAccess(String refreshToken, String scope, MultiValueMap<String, String> multiValueMap) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.set("client_id", clientId);
        params.set("refresh_token", refreshToken);
        if (scope != null) {
            params.set("scope", scope);
        }
        params.set("grant_type", "refresh_token");
        if (multiValueMap != null) {
            params.putAll(multiValueMap);
        }
        return postForAccessGrant(exchangeForAccessTokenUri(), params);
    }

    @Override
    public AccessGrant refreshAccess(String refreshToken, MultiValueMap<String, String> multiValueMap) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.set("client_id", clientId);
        params.set("refresh_token", refreshToken);
        params.set("grant_type", "refresh_token");
        if (multiValueMap != null) {
            params.putAll(multiValueMap);
        }
        return postForAccessGrant(exchangeForAccessTokenUri(), params);
    }



    @Override
    public AccessGrant authenticateClient() {
        return this.authenticateClient(null);
    }

    @Override
    public AccessGrant authenticateClient(String scope) {
        LinkedMultiValueMap params = new LinkedMultiValueMap();
        if(this.useParametersForClientAuthentication) {
            params.set("client_id", this.clientId);
        }

        params.set("grant_type", "client_credentials");
        if(scope != null) {
            params.set("scope", scope);
        }

        return this.postForAccessGrant(exchangeForAccessTokenUri(), params);
    }

    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_FORM_URLENCODED));
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(parameters, headers);
        return extractAccessGrant(resource.postForObject(accessTokenUrl, request, Map.class));
    }

    private AccessGrant extractAccessGrant(Map<String, Object> result) {
        return createAccessGrant( getValue(result ,"access_token"),  getValue(result ,"scope"), getValue(result ,"refresh_token"), getLongValue(result, "expires_in"), result);
    }

    protected AccessGrant createAccessGrant(String accessToken, String scope, String refreshToken, long expiresIn, Map<String, Object> response) {
        return new AccessGrant(accessToken, scope, refreshToken, expiresIn);
    }


    private String buildAuthUrl(String baseAuthUrl, GrantType grantType, OAuth2Parameters parameters){
        StringBuilder authUrl = new StringBuilder(baseAuthUrl);
        if (grantType == GrantType.AUTHORIZATION_CODE) {
            authUrl.append('&').append("response_type").append('=').append("code");
        } else if (grantType == GrantType.IMPLICIT_GRANT) {
            authUrl.append('&').append("response_type").append('=').append("token");
        }
        for (Iterator<Map.Entry<String, List<String>>> additionalParams = parameters.entrySet().iterator(); additionalParams.hasNext();) {
            Map.Entry<String, List<String>> param = additionalParams.next();
            String name = formEncode(param.getKey());
            for (Iterator<String> values = param.getValue().iterator(); values.hasNext();) {
                authUrl.append('&').append(name).append('=').append(formEncode(values.next()));
            }
        }
        return authUrl.toString();
    }

    protected RestTemplate createRestTemplate() {
        ClientHttpRequestFactory requestFactory = ClientHttpRequestFactorySelector.getRequestFactory();
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        ArrayList converters = new ArrayList(2);
        converters.add(new FormHttpMessageConverter());
        converters.add(new FormMapHttpMessageConverter());
        converters.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(converters);
        return restTemplate;
    }

    private String exchangeForAccessTokenUri() {
        OAuth2AuthorizeDiscoveryService discoveryService = new OAuth2AuthorizeDiscoveryService();
        AuthorizeEndpoint endpoint = discoveryService.exchangeForAuthorizeUri(organizationId, crmVersion, clientSdkVersion);
        return endpoint.parseTokenUri();
    }

    private String formEncode(String data) {
        try {
            return URLEncoder.encode(data, "UTF-8");
        }
        catch (UnsupportedEncodingException ex) {
            // should not happen, UTF-8 is always supported
            throw new IllegalStateException(ex);
        }
    }

    private String getValue(Map<String, Object> result, String key){
        LinkedList value = (LinkedList) result.get(key);
        if(value != null){
            return (String) value.getFirst();
        } else {
            return null;
        }
    }

    private Long getLongValue(Map<String, Object> map, String key) {
        try {
            LinkedList value = (LinkedList) map.get(key);
            if(value != null){
                return Long.valueOf(String.valueOf(value)); // normalize to String before creating integer value;
            } else {
                return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
