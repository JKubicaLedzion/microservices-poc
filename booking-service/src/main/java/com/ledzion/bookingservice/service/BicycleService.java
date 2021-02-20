package com.ledzion.bookingservice.service;

import com.ledzion.bookingservice.model.Bicycle;
import com.ledzion.bookingservice.model.BookingParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class BicycleService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String BICYCLE_SERVICE_URL = "http://bicycle-service/bicycles/";

    public List<Bicycle> getBicyclesByTypeSize(String type, String size) {
        return restTemplate.exchange( BICYCLE_SERVICE_URL + "filter?" + getFilterUrlPart(type, size),
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Bicycle>>() {})
                .getBody();
    }
    // TODO: check status code
    public boolean bicycleAvailable(long id, LocalDate startDate, LocalDate endDate) {
        return restTemplate.getForObject( BICYCLE_SERVICE_URL + id + getAvailabilityUrlPart(startDate, endDate),
                String.class).equals("Bicycle available.");
    }

    public void addBooking(BookingParameters bookingParameters) {
        restTemplate.put( BICYCLE_SERVICE_URL + "booking", bookingParameters);
    }

    private String getFilterUrlPart(String type, String size) {
        if(Objects.isNull(type)){
            return Objects.isNull(size) ? "" : "size=" + size;
        }
        return "type=" + type + (Objects.isNull(size) ? "" : "&size=" + size);
    }

    private String getAvailabilityUrlPart(LocalDate startDate, LocalDate endDate) {
        return "/availability?" + "startDate=" + startDate + "&endDate=" + endDate;
    }
}
