package org.springframework.social.dynamicscrm.api.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.social.dynamicscrm.api.ODataOperations;
import org.springframework.social.dynamicscrm.api.domain.odata.ODataQuery;
import org.springframework.social.dynamicscrm.rest.RestService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by psmelser on 2015-11-30.
 *
 * @author paul_smelser@silanis.com
 */
public class  ODataTemplate extends AbstractTemplate implements ODataOperations {
    private final RestService restService;
    private String baseUrl;


    public ODataTemplate(RestService restService, boolean isAuthorized, String baseUrl) {
        super(isAuthorized);
        this.restService = restService;
        this.baseUrl = baseUrl;
    }

    @Override
    public <T, R> R post(String entityPath, T entity, Class<R> responseType) {
        checkAuthorization();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        return restService.post(baseUrl, entityPath, new HttpEntity<T>(entity, responseHeaders), responseType);
    }

    public <T> T get(String entityPath, Class<T> responseType, ODataQuery oDataQuery){
        checkAuthorization();

        if (oDataQuery != null) {
            return restService.get(createUrl(entityPath, oDataQuery), responseType);
        }
        return restService.get(entityPath, responseType);
    }

    String createUrl(String url, ODataQuery oDataQuery) {

        if (oDataQuery.any()) {
            url += "?" + oDataQuery.next();
            String currentEl;
            while((currentEl =oDataQuery.next()) != null){
                url += "&" + currentEl;
            }
            oDataQuery.reset();
        }
        try {
            url = URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }


}
