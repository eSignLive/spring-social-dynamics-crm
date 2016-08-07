package org.springframework.social.dynamicscrm.api.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Optional;
import org.springframework.social.connect.DynamicsCrmProfile;
import org.springframework.social.dynamicscrm.api.UserOperations;
import org.springframework.social.dynamicscrm.connect.url.DefaultDynamicsUrlProvider;
import org.springframework.social.dynamicscrm.rest.RestService;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

/**
 * Created by psmelser on 2016-02-28.
 *
 * @author paul.smelser@esignlive.com
 */
public class UserTemplate extends AbstractTemplate implements UserOperations {

    private final RestService restService;
    private final String baseUri;

    protected UserTemplate(RestService restService, boolean isAuthorized, String baseUri) {
        super(isAuthorized);
        this.restService = restService;
        this.baseUri = baseUri;
    }

    @Override
    public DynamicsCrmProfile getUserProfile() {
        checkAuthorization();
        return restService.get(URI.create(baseUri + "/whoami"), DynamicsCrmProfile.class);
    }

    @Override
    public Optional<String> internalEmailAddressOf(String windowsLiveID) {
        checkAuthorization();
        try {
            //TODO: USE ODataQuery and make it available in ODataOperations
            String queryUrl = UriComponentsBuilder.fromUriString(baseUri + "/" + DefaultDynamicsUrlProvider.API_PATH + "/SystemUserSet")
                    .queryParam(encode("$select"), encode("InternalEMailAddress"))
                    .queryParam(encode("$filter"), encode("startswith(WindowsLiveID, '" + windowsLiveID + "')")).build().toUriString();
            String responseJson = restService.get(URI.create(queryUrl), String.class);
            return extract(responseJson, "InternalEMailAddress");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.absent();
    }

    private String encode(String var) throws UnsupportedEncodingException {
        return URLEncoder.encode(var, "UTF-8");
    }

    private Optional<String> extract(String json, String field) throws IOException {
        ObjectNode objectNode = new ObjectMapper().readValue(json, ObjectNode.class);
        JsonNode emailNode = objectNode.findValue(field);
        return emailNode != null ? Optional.of(emailNode.asText()) : Optional.<String>absent();
    }
}
