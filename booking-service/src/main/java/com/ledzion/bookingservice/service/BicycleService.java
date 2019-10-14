package com.ledzion.bookingservice.service;

import com.ledzion.bookingservice.model.Bicycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class BicycleService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String BICYCLE_SERVICE_URL = "http://bicycle-service/bicycles/";

    public Bicycle getBicycleById(long id) {
        return new RestTemplate().getForObject( BICYCLE_SERVICE_URL + id, Bicycle.class );
    }

    public List<Bicycle> getBicyclesByTypeSize(String type, String size) {
        return restTemplate.exchange( BICYCLE_SERVICE_URL + "filter?" + getFilterUrlPart(type, size),
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Bicycle>>() {})
                .getBody();
    }

    private String getFilterUrlPart(String type, String size) {
        if(Objects.isNull(type)){
            return Objects.isNull(size) ? "" : "size=" + size;
        }
        return "type=" + type + (Objects.isNull(size) ? "" : "&size=" + size);
    }
}
