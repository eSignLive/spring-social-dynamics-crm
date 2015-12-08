package org.springframework.social.dynamicscrm.api.impl;

import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.social.dynamicscrm.api.DynamicsCrm;
import org.springframework.social.dynamicscrm.api.ODataOperations;
import org.springframework.social.dynamicscrm.api.UserOperations;
import org.springframework.social.dynamicscrm.rest.RestService;
import org.springframework.social.dynamicscrm.rest.errorhandling.DynamicsCrmRestTemplateErrorHandler;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by psmelser on 2015-11-20.
 *
 * @author paul_smelser@silanis.com
 */
public class DynamicsCrmTemplate extends AbstractOAuth2ApiBinding implements DynamicsCrm {

    private UserTemplate userOperations;
    private ODataOperations notificationOperations;

    public DynamicsCrmTemplate(String accessToken, String baseApiUrl, String baseUploadUrl){
        super(accessToken);

        RestService restService = new RestService(getRestTemplate());

        userOperations = new UserTemplate(restService, isAuthorized(), baseApiUrl);

        notificationOperations = new ODataTemplate(restService, isAuthorized(), baseApiUrl);
    }

    @Override
    protected List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new StringHttpMessageConverter());
        messageConverters.add(getFormMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        messageConverters.add(getByteArrayMessageConverter());

        ByteArrayHttpMessageConverter byteArrayMessageConverter = getByteArrayMessageConverter();
        byteArrayMessageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(byteArrayMessageConverter);

//        messageConverters.addAll(getMessageConverters());
        return messageConverters;
    }
    @Override
    protected void configureRestTemplate(RestTemplate restTemplate) {
        restTemplate.setErrorHandler(new DynamicsCrmRestTemplateErrorHandler());
    }

    @Override
    public UserOperations userOperations() {
        return userOperations;
    }

    @Override
    public ODataOperations oDataOperations(){
        return notificationOperations;
    }
}
