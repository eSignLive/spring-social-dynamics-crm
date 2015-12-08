package org.springframework.social.dynamicscrm.api.impl;

import java.util.Map;

/**
 * Created by psmelser on 2015-12-04.
 *
 * @author paul_smelser@silanis.com
 */
public class Result {
    private String status;
    private Map<String, String> entries;

    public Result(){}
    public String getStatus() {
        return status;
    }

    public Result setStatus(String status) {
        this.status = status;
        return this;
    }

    public Map<String, String> getEntries() {
        return entries;
    }

    public Result setEntries(Map<String, String> entries) {
        this.entries = entries;
        return this;
    }
}
