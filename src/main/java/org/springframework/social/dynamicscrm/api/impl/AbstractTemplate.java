package org.springframework.social.dynamicscrm.api.impl;

import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.dynamicscrm.api.DynamicsCrm;

/**
 * Created by psmelser on 2015-11-20.
 *
 * @author paul_smelser@silanis.com
 */
public class AbstractTemplate {
    public void checkAuthorization(boolean isAuthorized){
        if (!isAuthorized) {
            throw new MissingAuthorizationException(DynamicsCrm.PROVIDER_NAME);
        }
    }
}
