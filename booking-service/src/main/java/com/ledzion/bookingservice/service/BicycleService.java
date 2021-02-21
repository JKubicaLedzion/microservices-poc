package com.ledzion.bookingservice.service;

import com.ledzion.bookingservice.exception.BadRequest;
import com.ledzion.bookingservice.model.Bicycle;
import com.ledzion.bookingservice.model.BookingParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
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
        try {
            return restTemplate.exchange( BICYCLE_SERVICE_URL + "filter?" + getFilterUrlPart(type, size),
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Bicycle>>() {})
                    .getBody();
        } catch (final HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new BadRequest(e.getResponseBodyAsString());
            }
        }//TODO
        return null;
    }

    public boolean bicycleAvailable(long id, LocalDate startDate, LocalDate endDate) {
        try {
            return restTemplate.exchange(BICYCLE_SERVICE_URL + id + getAvailabilityUrlPart(startDate, endDate),
                    HttpMethod.GET, null, String.class).getStatusCode().equals(HttpStatus.OK);

        } catch (final HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new BadRequest(e.getResponseBodyAsString());
            }
            return false;
        }
    }

    public void addBooking(BookingParameters bookingParameters) {
        try {
            restTemplate.put( BICYCLE_SERVICE_URL + "booking", bookingParameters);
        } catch(final HttpClientErrorException e) {
            if(e.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                throw new BadRequest(e.getResponseBodyAsString());
            }
        }
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
