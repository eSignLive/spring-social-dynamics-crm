package org.springframework.social.oauth2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by psmelser on 2015-12-18.
 *
 * @author paul_smelser@silanis.com
 */
public class OAuth2Endpoint {
    private String authUrl;

    private OAuth2Endpoint(String authUrl){
        this.authUrl = authUrl;
    }
    public String getAuthUrl() {
        return authUrl;
    }

    public static OAuth2Endpoint parseAuthUrl(String response) {
        Pattern r = Pattern.compile("authorization_uri=(\\S*)");
        Matcher m = r.matcher(response);
        m.find();

        return new OAuth2Endpoint(m.group(1));
    }

    public String parseTenantId() {
        Pattern pattern = Pattern.compile(".*/([a-zA-Z0-9]{8}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{12}).*");
        Matcher matcher = pattern.matcher(authUrl);
        matcher.find();
        return matcher.group(1);
    }

    public String parseTokenUri(){
        return authUrl.replace("authorize", "token");
    }
}
