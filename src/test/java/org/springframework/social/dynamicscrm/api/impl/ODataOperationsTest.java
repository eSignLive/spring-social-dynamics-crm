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
import org.springframework.social.dynamicscrm.api.domain.odata.ODataQuery;
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

    private DynamicsCrm dynamicsCrm;
    private MockRestServiceServer mockServer;
    ObjectMapper mapper = new ObjectMapper();

    @Before
    public void before(){
        dynamicsCrm = new DynamicsCrmTemplate("cGF1bHNtZWxzZXJAc2lsYW5pc2luYy5vbm1pY3Jvc29mdC5jb206c2lsYW5pczE==", DefaultDynamicsUrlProvider.API_URL, DefaultDynamicsUrlProvider.UPLOAD_URL);

        mockServer = MockRestServiceServer.createServer(dynamicsCrm.getRestTemplate());
    }

    @Test
    public void testNotify() throws Exception {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

        mockServer.expect(requestTo(DefaultDynamicsUrlProvider.API_URL+"/esl_notificationSet"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withResponse(notifyTestJson(), responseHeaders));

        Result result = dynamicsCrm.oDataOperations().save("/esl_notificationSet", accountJson(), Result.class);
    }

    @Test
    public void testODataFilter(){
        ODataQuery oDataQuery = new ODataQuery()
                .orderBy("Name")
                .skip(10)
                .top(100)
                .withFilter("AccountCategoryCode/Something eq 1")
                .withSelect("select hi");

    }
    @Test
    public void testSerializeToString(){
        System.out.println(accountJson());
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