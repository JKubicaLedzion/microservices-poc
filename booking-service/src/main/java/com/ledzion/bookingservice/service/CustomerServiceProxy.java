//package com.ledzion.bookingservice.service;
//
//import com.ledzion.bookingservice.model.Customer;
//import org.springframework.cloud.netflix.feign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//@FeignClient(name="customer-service", url="localhost:8082")
//public interface CustomerServiceProxy {
//
//    @GetMapping("/customers/{id}")
//    public Customer getCustomerById(@PathVariable(name = "id") long id);
//}
