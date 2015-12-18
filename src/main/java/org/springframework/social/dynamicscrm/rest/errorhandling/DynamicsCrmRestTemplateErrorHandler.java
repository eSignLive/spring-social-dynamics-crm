package org.springframework.social.dynamicscrm.rest.errorhandling;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Created by psmelser on 2015-11-20.
 *
 * @author paul_smelser@silanis.com
 */
public class DynamicsCrmRestTemplateErrorHandler  extends DefaultResponseErrorHandler implements ResponseErrorHandler {
    private static final Logger log = LoggerFactory.getLogger(DynamicsCrmRestTemplateErrorHandler.class.getName());

    private final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DynamicsCrmRestTemplateErrorHandler.class.getName());
    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        HttpStatus statusCode = clientHttpResponse.getStatusCode();
        return statusCode.series() == HttpStatus.Series.CLIENT_ERROR || statusCode.series() == HttpStatus.Series.SERVER_ERROR;
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
        logger.log(Level.WARNING, clientHttpResponse.getStatusText() + ": " + clientHttpResponse.getStatusText());
    }
}
