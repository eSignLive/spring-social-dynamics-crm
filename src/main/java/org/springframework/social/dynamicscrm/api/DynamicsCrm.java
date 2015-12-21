package org.springframework.social.dynamicscrm.api;

import org.springframework.social.ApiBinding;

/**
 * Created by psmelser on 2015-11-20.
 *
 * @author paul_smelser@silanis.com
 */
public interface DynamicsCrm extends ApiBinding {
    String PROVIDER_NAME = "dynamicscrm";
    ODataOperations oDataOperations();
}
