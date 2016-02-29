package org.springframework.social.dynamicscrm.api.impl;

import org.springframework.social.connect.DynamicsCrmProfile;
import org.springframework.social.dynamicscrm.api.UserOperations;
import org.springframework.social.dynamicscrm.rest.RestService;

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
        return restService.get(baseUri + "/whoami", DynamicsCrmProfile.class);
    }
}
