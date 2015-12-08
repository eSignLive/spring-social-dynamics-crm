package org.springframework.social.dynamicscrm.api.impl;

import org.springframework.http.HttpEntity;
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

    public static final String ORDER_BY = "$orderBy";
    public static final String SKIP = "$skip";
    public static final String TOP = "$top";
    private static final String FILTER = "$filter=";
    private static final String SELECT = "$select";

    private final RestService restService;
    private boolean isAuthorized;
    private String baseUrl;


    public ODataTemplate(RestService restService, boolean isAuthorized, String baseUrl) {
        this.restService = restService;
        this.isAuthorized = isAuthorized;
        this.baseUrl = baseUrl;
    }

    @Override
    public <T, R> R save(String url, T entity, Class<R> responseType) {
        checkAuthorization(isAuthorized);

        return restService.post(baseUrl, url, new HttpEntity<T>(entity), responseType);
    }

    public <T> T get(String url, Class<T> responseType, ODataQuery oDataQuery){
        checkAuthorization(isAuthorized);

        if (oDataQuery != null) {
            return restService.get(createUrl(url, oDataQuery), responseType);
        }
        return restService.get(url, responseType);
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
