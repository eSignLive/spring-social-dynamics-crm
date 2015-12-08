package org.springframework.social.dynamicscrm.rest.errorhandling;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 * Created by psmelser on 2015-11-20.
 *
 * @author paul_smelser@silanis.com
 */
public class DynamicsCrmRestTemplateErrorHandler  extends DefaultResponseErrorHandler implements ResponseErrorHandler {
    private static final Logger log = LoggerFactory.getLogger(DynamicsCrmRestTemplateErrorHandler.class.getName());

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        if(super.hasError(response)){
            log.info("Error Response headers: {}", response.getHeaders());
            log.info("Error Response body: {}", IOUtils.toString(response.getBody()));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        super.handleError(response);
    }
}
