package org.springframework.social.dynamicscrm.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.dynamicscrm.api.DynamicsCrm;

/**
 * Created by psmelser on 2015-12-04.
 *
 * @author paul_smelser@silanis.com
 */
public class DynamicsCrmAdapter implements ApiAdapter<DynamicsCrm> {
    @Override
    public boolean test(DynamicsCrm dynamicsCrm) {
        return false;
    }

    @Override
    public void setConnectionValues(DynamicsCrm dynamicsCrm, ConnectionValues connectionValues) {
        connectionValues.setProviderUserId("paulsmelser@silanisinc.onmicrosoft.com");
        connectionValues.setDisplayName("psmelser");
    }

    @Override
    public UserProfile fetchUserProfile(DynamicsCrm dynamicsCrm) {
        return null;
    }

    @Override
    public void updateStatus(DynamicsCrm dynamicsCrm, String s) {

    }
}
