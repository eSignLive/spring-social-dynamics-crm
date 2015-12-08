package org.springframework.social.dynamicscrm.api.impl;

import org.springframework.social.dynamicscrm.api.UserOperations;
import org.springframework.social.dynamicscrm.api.domain.DynamicsCrmProfile;
import org.springframework.social.dynamicscrm.rest.RestService;


/**
 * Created by psmelser on 2015-11-20.
 *
 * @author paul_smelser@silanis.com
 */
public class UserTemplate extends AbstractTemplate implements UserOperations{

    private final RestService restService;

    private boolean isAuthorized;

    private String url;

    public UserTemplate(RestService restService, boolean isAuthorized, String baseUrl){
        this.restService = restService;
        this.isAuthorized = isAuthorized;
        this.url = baseUrl + "/users";
    }

    @Override
    public DynamicsCrmProfile getUserProfile() {
        return null;
    }
}
