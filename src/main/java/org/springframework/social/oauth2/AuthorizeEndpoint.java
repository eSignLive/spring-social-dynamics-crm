package org.springframework.social.oauth2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by psmelser on 2015-12-18.
 *
 * @author paul_smelser@silanis.com
 */
public class AuthorizeEndpoint {
    private String authUrl;
    private String resourceId;

    private AuthorizeEndpoint(String authUrl, String resourceId){
        this.authUrl = authUrl;
        this.resourceId = resourceId;
    }
    public String getAuthUrl() {
        return authUrl;
    }

    public String getResourceId() {
        return resourceId;
    }

    public static AuthorizeEndpoint parseAuthUrl(String response) {
        Pattern r = Pattern.compile("authorization_uri=(\\S*),\\s*resource_id=(\\S*)");
        Matcher m = r.matcher(response);
        m.find();

        return new AuthorizeEndpoint(m.group(1), m.group(2));
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
