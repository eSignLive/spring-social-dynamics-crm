package org.springframework.social.connect;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by psmelser on 2015-11-20.
 *
 * @author paul_smelser@silanis.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DynamicsCrmProfile extends UserProfile{

    @JsonProperty("BusinessUnitId")
    public String businessUnitId;
    @JsonProperty("UserId")
    public String userId;
    @JsonProperty("OrganizationId")
    public String organizationId;

    DynamicsCrmProfile(String name, String firstName, String lastName, String email, String username) {
        super(name, firstName, lastName, email, username);
    }

    public String getBusinessUnitId() {
        return businessUnitId;
    }

    public DynamicsCrmProfile setBusinessUnitId(String businessUnitId) {
        this.businessUnitId = businessUnitId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public DynamicsCrmProfile setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public DynamicsCrmProfile setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
        return this;
    }
}
