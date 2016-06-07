package org.springframework.social.dynamicscrm.rest;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static java.lang.String.format;

/**
 * Created by psmelser on 2015-11-20.
 *
 * @author paul_smelser@silanis.com
 */
public class RestService {
    private RestTemplate restTemplate;

    public RestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> T get(String url, ParameterizedTypeReference<T> responseType) {
        return restTemplate.exchange(URI.create(url), HttpMethod.GET, null, responseType).getBody();
    }

    public <T> T get(String url, Class<T> responseType) {
        ResponseEntity<T> body = restTemplate.exchange(URI.create(url), HttpMethod.GET, null, responseType);
        return body.getBody();

    }

    public <T> T post(String url, ParameterizedTypeReference<T> responseType) {
        return restTemplate.exchange(URI.create(url), HttpMethod.POST, HttpEntity.EMPTY, responseType).getBody();
    }

    public <T, R> R post(String baseUrl, String path, HttpEntity<T> entity, Class<R> responseType) {
        return restTemplate.exchange(
                URI.create(format("%s/%s", baseUrl, path.replace("/", ""))),
                HttpMethod.POST,
                entity,
                responseType)
                .getBody();
    }
}
