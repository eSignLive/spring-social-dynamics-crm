package org.springframework.social.dynamicscrm.api;

import org.springframework.http.ResponseEntity;

/**
 * Created by psmelser on 2015-11-30.
 *
 * @author paul_smelser@silanis.com
 */
public interface ODataOperations {
    <T, R> ResponseEntity<R> post(String url, T entity, Class<R> returnClass);

}
