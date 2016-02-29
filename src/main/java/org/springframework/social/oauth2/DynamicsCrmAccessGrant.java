package org.springframework.social.oauth2;

/**
 * Created by psmelser on 2016-02-29.
 *
 * @author paul.smelser@esignlive.com
 */
public class DynamicsCrmAccessGrant extends AccessGrant{
    private String id_token;

    public DynamicsCrmAccessGrant(String accessToken, String scope, String refreshToken, String id_token, Long expiresIn) {
        super(accessToken, scope, refreshToken, expiresIn);
        this.id_token = id_token;
    }

    public String getId_token() {
        return id_token;
    }

    public DynamicsCrmAccessGrant setId_token(String id_token) {
        this.id_token = id_token;
        return this;
    }
}
