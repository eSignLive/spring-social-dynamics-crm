package org.springframework.social.dynamicscrm.api.impl;

import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.dynamicscrm.api.DynamicsCrm;

/**
 * Created by psmelser on 2015-11-20.
 *
 * @author paul_smelser@silanis.com
 */
public class AbstractTemplate {
    private boolean isAuthorized;

    protected AbstractTemplate(boolean isAuthorized){
        this.isAuthorized = isAuthorized;
    }
    public void checkAuthorization(){
        if (!isAuthorized) {
            throw new MissingAuthorizationException(DynamicsCrm.PROVIDER_NAME);
        }
    }
}
