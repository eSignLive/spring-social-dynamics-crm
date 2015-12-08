package org.springframework.social.dynamicscrm.api.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.dynamicscrm.api.domain.odata.ODataQuery;
import org.springframework.social.dynamicscrm.rest.RestService;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by psmelser on 2015-12-07.
 *
 * @author paul_smelser@silanis.com
 */
public class ODataTemplateTest {

    public static final String HTTPS_TEST_COM = "https://test.com";
    RestTemplate restTemplate = mock(RestTemplate.class);
    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testCreateUrl() throws Exception {
        ODataQuery oDataQuery = new ODataQuery()
                .orderBy("Name")
                .skip(10)
                .top(100)
                .withSelect("Name")
                .withFilter("AccountCategoryCode/Something eq 1"
                );

        ODataTemplate template = new ODataTemplate(new RestService(restTemplate), true, HTTPS_TEST_COM);
        String url = template.createUrl(HTTPS_TEST_COM, oDataQuery);

        when(restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, String.class)).thenReturn(new ResponseEntity<String>("Hello", HttpStatus.ACCEPTED));

        template.get(HTTPS_TEST_COM, String.class, oDataQuery);

        Mockito.verify(restTemplate).exchange(url, HttpMethod.GET, null, String.class);

        System.out.println(template.createUrl(HTTPS_TEST_COM, oDataQuery));
    }

    private String responseJson() throws JsonProcessingException {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        ObjectNode node = mapper.createObjectNode();

        node.put("status", "OK")
            .put("body", "Hello");
        return node.toString();
    }
}