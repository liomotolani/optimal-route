package com.klasha.logistics.util;


import com.klasha.logistics.dto.input.DeliveryInputDTO;
import com.klasha.logistics.dto.output.RouteResponseDTO;
import com.klasha.logistics.model.Routes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@Slf4j
public class RestUtils {

    @Value("${here.route.base.url}")
    private String baseUrl;

    @Value("${here.api.key}")
    private String apiKey;

    public List<Routes> getRoutes(DeliveryInputDTO dto, String countries){

        RestTemplate restTemplate = getRestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();

        HttpEntity entity = new HttpEntity(httpHeaders);
        String origin = dto.getOrigin().getLat() + "," + dto.getOrigin().getLng();
        String destination = dto.getDestination().getLat() + "," + dto.getDestination().getLng();
        String travelMode = dto.getTravelMode().equals("") ? "car" : dto.getTravelMode();

        String url =  baseUrl+"?origin="+origin+"&destination="+destination+"&return=summary,actions,polyline"+
                "&transportMode="+travelMode+"&exclude[countries]="+countries+
                "&apikey="+apiKey+"&alternatives=1";
        log.info("{}", url);
        ResponseEntity<RouteResponseDTO> response;

        try {
            response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    RouteResponseDTO.class);
        }catch (HttpClientErrorException e) {
            throw new IllegalStateException(e);
        }
        log.info("{}",response);

        return response.getBody().getRoutes();

    }

    private static RestTemplate getRestTemplate() {
        CloseableHttpClient httpClient
                = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
        HttpComponentsClientHttpRequestFactory requestFactory
                = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        return new RestTemplate(requestFactory);
    }
}
