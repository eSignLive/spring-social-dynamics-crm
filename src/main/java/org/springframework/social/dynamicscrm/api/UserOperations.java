package org.springframework.social.dynamicscrm.api;

import com.google.common.base.Optional;
import org.springframework.social.connect.DynamicsCrmProfile;

/**
 * Created by psmelser on 2016-02-28.
 *
 * @author paul.smelser@esignlive.com
 */
public interface UserOperations {
    DynamicsCrmProfile getUserProfile();
    Optional<String> internalEmailAddressOf(String windowsLiveID);
}
