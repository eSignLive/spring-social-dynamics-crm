package org.springframework.social.dynamicscrm.connect.url;

/**
 * Created by psmelser on 2015-11-20.
 *
 * @author paul_smelser@silanis.com
 */
public interface DynamicsUrlProvider {
    String getApiUrl();
    String getOauth2AuthorizationUrl();
    String getOauth2TokenUrl();
}
