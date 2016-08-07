package org.springframework.social.dynamicscrm.api.impl;

import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.social.dynamicscrm.api.DynamicsCrm;
import org.springframework.social.dynamicscrm.connect.url.DefaultDynamicsUrlProvider;
import org.springframework.social.test.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.social.test.client.RequestMatchers.method;
import static org.springframework.social.test.client.RequestMatchers.requestTo;
import static org.springframework.social.test.client.ResponseCreators.withResponse;

/**
 * Created by kkumar on 07/08/16.
 */
public class UserOperationsTest {

    private MockRestServiceServer mockServer;
    private DynamicsCrm dynamicsCrm;

    @Before
    public void setup(){
        dynamicsCrm = new DynamicsCrmTemplate("anAccessToken", DefaultDynamicsUrlProvider.API_BASE_URL);
        RestTemplate restTemplate = ((DynamicsCrmTemplate) dynamicsCrm).getRestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void whenFindingInternalEmailAddressThenReturnCorrespondingEmailAddress() throws UnsupportedEncodingException {
        String windowsLiveID = "windowsUsername@silanisinc.onmcirsoft.com";
        String expectedInternalEmail = "dy-online-01@e-signlivetest.com";
        String responseJson="{" +
                "\"d\" : {" +
                "\"results\": [" +
                "{" +
                "\"__metadata\": {" +
                "\"uri\": \"https://silanisinc.crm.dynamics.com/XRMServices/2011/OrganizationData.svc/SystemUserSet(guid'470d9107-6453-e611-80fe-3863bb2e5390')\", \"type\": \"Microsoft.Crm.Sdk.Data.Services.SystemUser\"" +
                "}, \"InternalEMailAddress\": \"" + expectedInternalEmail + "\"" +
                "}" +
                "]" +
                "}" +
                "}";
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

        mockServer.expect(requestTo(microsoftUrl(windowsLiveID)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withResponse(responseJson, responseHeaders));


        Optional<String> emailOptional = dynamicsCrm.userOperations().internalEmailAddressOf(windowsLiveID);


        assertThat(emailOptional.get(), is(equalTo(expectedInternalEmail)));
    }

    private String microsoftUrl(String windowsLiveID) throws UnsupportedEncodingException {
        return UriComponentsBuilder.fromUriString("https://silanisinc.api.crm.dynamics.com/" + DefaultDynamicsUrlProvider.API_PATH + "/SystemUserSet")
                    .queryParam(encode("$select"), encode("InternalEMailAddress"))
                    .queryParam(encode("$filter"), encode("startswith(WindowsLiveID, '" + windowsLiveID + "')")).build().toUriString();
    }

    private String encode(String var) throws UnsupportedEncodingException {
        return URLEncoder.encode(var, "UTF-8");
    }
}
