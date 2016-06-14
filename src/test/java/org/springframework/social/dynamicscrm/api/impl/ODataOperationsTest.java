package org.springframework.social.dynamicscrm.api.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.social.dynamicscrm.api.DynamicsCrm;
import org.springframework.social.dynamicscrm.connect.url.DefaultDynamicsUrlProvider;
import org.springframework.social.test.client.MockRestServiceServer;

import static org.springframework.social.test.client.RequestMatchers.method;
import static org.springframework.social.test.client.RequestMatchers.requestTo;
import static org.springframework.social.test.client.ResponseCreators.withResponse;

/**
 * Created by psmelser on 2015-12-04.
 *
 * @author paul_smelser@silanis.com
 */
public class ODataOperationsTest {

    public static final String ACCOUNT_SET = "AccountSet";
    private DynamicsCrm dynamicsCrm;
    private MockRestServiceServer mockServer;
    ObjectMapper mapper = new ObjectMapper();

    @Before
    public void before(){
        dynamicsCrm = new DynamicsCrmTemplate("eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6Ik1uQ19WWmNBVGZNNXBPWWlKSE1iYTlnb0VLWSIsImtpZCI6Ik1uQ19WWmNBVGZNNXBPWWlKSE1iYTlnb0VLWSJ9.eyJhdWQiOiJodHRwczovL3NpbGFuaXNpbmMuY3JtLmR5bmFtaWNzLmNvbS8iLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC8yZWNjZjk0My0zM2ZiLTQ4OTMtOGYyMC0zZTczY2Y0NDE0YzQvIiwiaWF0IjoxNDUwMTI1NDIwLCJuYmYiOjE0NTAxMjU0MjAsImV4cCI6MTQ1MDEyOTMyMCwiYWNyIjoiMSIsImFtciI6WyJwd2QiXSwiYXBwaWQiOiIxZjVlN2U4Ny1lNDQ0LTQ4ZDktYTIwNi04YThhYmE5NmFhOTIiLCJhcHBpZGFjciI6IjAiLCJmYW1pbHlfbmFtZSI6IlNtZWxzZXIiLCJnaXZlbl9uYW1lIjoiUGF1bCIsImlwYWRkciI6IjY2LjQ2LjIxNy4xODYiLCJuYW1lIjoiUGF1bCBTbWVsc2VyIiwib2lkIjoiMTE0ZGI1YTItZDFmZi00Y2M3LWEzZjItMGFkY2IzZDQ2ZTA3IiwicHVpZCI6IjEwMDNCRkZEOTU1NDg4RUIiLCJzY3AiOiJ1c2VyX2ltcGVyc29uYXRpb24iLCJzdWIiOiI5LXNUQ0JhNFE0NFBUM3hzVGsyRnk5a0xxaV9FODFOUDE0cUdNR2RPa0ZNIiwidGlkIjoiMmVjY2Y5NDMtMzNmYi00ODkzLThmMjAtM2U3M2NmNDQxNGM0IiwidW5pcXVlX25hbWUiOiJwc21lbHNlckBzaWxhbmlzaW5jLm9ubWljcm9zb2Z0LmNvbSIsInVwbiI6InBzbWVsc2VyQHNpbGFuaXNpbmMub25taWNyb3NvZnQuY29tIiwidmVyIjoiMS4wIiwid2lkcyI6WyI2MmU5MDM5NC02OWY1LTQyMzctOTE5MC0wMTIxNzcxNDVlMTAiXX0.ZCp7KoDH0q4GOca7uV8OrbfZBYVVTOL5OoxkZjJwTNHwWMw6lmMjuJ8xFpi_1MlU8T0uSG_ezwgiqaNJwcqaSDD4QJRRdL2NgxMRMPKE7LVBPdUrBDs7iOE73lJi-HyyGFuNUS23r9sSf6dguzgecGcuJa7q1OcsQ-1HqnSbucUgDk4xUBmvb1aA6hJ9eVVq082JSS3ljawBOpBO6ZqpanMBkGvCwFZmYczGL1Zxp7PM2yAQSH_wQMXFOKmRgjAlcpBqkoR7bKE9rKhC5JvDeZEVMyimBjQo7veIRFSgDp4Z6WY_Aq6jEm8lX_MZsUOQhq9W7b8YRk5VoT8yUkaX8w",
                DefaultDynamicsUrlProvider.API_BASE_URL);

        mockServer = MockRestServiceServer.createServer(((DynamicsCrmTemplate) dynamicsCrm).getRestTemplate());
    }

    @Test
    public void whenSavingTheRestServiceReceivesTheExpectedRequest() throws Exception {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

        mockServer.expect(requestTo(DefaultDynamicsUrlProvider.API_URL + "/" + ACCOUNT_SET))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withResponse(notifyTestJson(), responseHeaders));

        dynamicsCrm.oDataOperations().post(ACCOUNT_SET, accountJson(), String.class);
    }


    private String notifyTestJson() throws JsonProcessingException {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        ObjectNode node = mapper.createObjectNode();

        node.put("status", "OK")
            .put("entries",
                    mapper.createObjectNode()
                            .put("hello", "hi")
                            .put("goodbye", "later")
            );
        return node.toString();
    }


    private String accountJson(){
        ObjectNode objectNode = mapper.createObjectNode();
        return objectNode.put("Name", "Hello").toString();
    }
}