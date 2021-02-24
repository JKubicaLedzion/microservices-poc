package com.ledzion.customerservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ledzion.customerservice.model.Address;
import com.ledzion.customerservice.model.BookingParameters;
import com.ledzion.customerservice.model.BookingPeriod;
import com.ledzion.customerservice.model.Customer;
import com.ledzion.customerservice.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    private static final String PATH = "/customers/";

    private List<Customer> customers = new ArrayList<>();

    private BookingParameters bookingParameters = new BookingParameters("1", "1", LocalDate.parse("2021-04-13"),
            LocalDate.parse("2021-04-13"));

    private static ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private CustomerController customerController;

    @Before
    public void setup() {
        customers.clear();
        customers.add(getFirstTestCustomer());
        customers.add(getSecondTestCustomer());

        mvc = MockMvcBuilders
                .standaloneSetup(customerController)
                .build();
    }

    @Test
    public void getCustomerByIdShouldReturnOkResponseAndContentWithCustomer() throws Exception {
        // given:
        Mockito.when(customerService.getCustomerById("1")).thenReturn(Optional.of(customers.get(0)));
        // when:
        mvc.perform(get(PATH + "1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                //then:
                .andExpect(status().isOk())
                .andExpect(content().string(is(getJsonFormatStringForCustomer(customers.get(0)))));
    }

    @Test
    public void getCustomerByIdShouldReturnNotFound() throws Exception {
        // given:
        Mockito.when(customerService.getCustomerById("3")).thenReturn(Optional.empty());
        // when:
        mvc.perform(get(PATH + "3")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                //then:
                .andExpect(status().isNotFound())
                .andExpect(content().string(is("Customer not found.")));
    }

    @Test
    public void getCustomersShouldReturnNotFound() throws Exception {
        // given:
        Mockito.when(customerService.getAllCustomers()).thenReturn(Collections.emptyList());
        // when:
        mvc.perform(get(PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                //then:
                .andExpect(status().isNotFound())
                .andExpect(content().string(is("Customer not found.")));
    }

    @Test
    public void getAllCustomersShouldReturnOkResponseAndContentWithCustomers() throws Exception {
        // given:
        Mockito.when(customerService.getAllCustomers()).thenReturn(customers);
        // when:
        mvc.perform(get(PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                //then:
                .andExpect(status().isOk())
                .andExpect(content().string(is("[" + getJsonFormatStringForCustomer(customers.get(0)) + ","
                                + getJsonFormatStringForCustomer(customers.get(1)) + "]")));
    }

//    @Test
//    public void addBookingShouldReturnOkResponseAndContentWithMessage() throws Exception {
//        // given:
//        Mockito.when(customerService.addBooking(bookingParameters)).thenReturn(true);
//
//        // when:
//        mvc.perform(put(PATH + "booking")
//                .content(getJsonFormatStringForBookingParameters(bookingParameters))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(MockMvcResultHandlers.print())
//                //then:
//                .andExpect(status().isOk())
//                .andExpect(content().string(is("Bicycle booking added.")));
//    }
//
//    @Test
//    public void addBookingShouldReturnBadRequestAndContentWithMessage() throws Exception {
//        // given:
//        Mockito.when(customerService.addBooking(bookingParameters)).thenReturn(false);
//
//        // when:
//        mvc.perform(put(PATH + "booking")
//                .content(getJsonFormatStringForBookingParameters(bookingParameters))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(MockMvcResultHandlers.print())
//                //then:
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string(is("Error while adding bicycle booking. Provided data incorrect.")));
//    }

    private Customer getFirstTestCustomer() {
        Address address = Address.builder()
                .country("Poland")
                .postalCode("333777")
                .city("Lodz")
                .street("Street")
                .number("1")
                .build();
        return Customer.builder()
                .id("1")
                .address(address)
                .name("Jan Kowalski")
                .email("Jan.Kowalski@aaa.com")
                .phone("123456789")
                .bookings(getBookingsForFirstCustomer())
                .build();
    }

    private Customer getSecondTestCustomer() {
        Address address = Address.builder()
                .country("Poland")
                .postalCode("333777")
                .city("Lodz")
                .street("Street")
                .number("1")
                .build();
        return Customer.builder()
                .id("2")
                .address(address)
                .name("Anna Kowalska")
                .email("Anna.Kowalska@aaa.com")
                .phone("987654321")
                .bookings(getBookingsForSecondCustomer())
                .build();
    }

    private String getJsonFormatStringForCustomer(Customer customer) throws JsonProcessingException {
        return mapper.writeValueAsString(customer);
    }

    private String getJsonFormatStringForBookingParameters(BookingParameters bookingParameters) throws JsonProcessingException {
        return mapper.writeValueAsString(bookingParameters);
    }

    private Map<String, List<BookingPeriod>> getBookingsForFirstCustomer(){
        return new HashMap<String, List<BookingPeriod>>(){{
            put("1", new ArrayList<BookingPeriod>() {{
                add(new BookingPeriod(java.time.LocalDate.parse("2021-01-01"), LocalDate.parse("2021-01-03")));
                add(new BookingPeriod(java.time.LocalDate.parse("2021-01-11"), LocalDate.parse("2021-01-11")));
            }});
            put("2", new ArrayList<BookingPeriod>() {{
                add(new BookingPeriod(java.time.LocalDate.parse("2021-01-01"), LocalDate.parse("2021-01-03")));
                add(new BookingPeriod(java.time.LocalDate.parse("2021-01-11"), LocalDate.parse("2021-01-11")));
            }});
        }};
    }

    private Map<String, List<BookingPeriod>> getBookingsForSecondCustomer(){
        return new HashMap<String, List<BookingPeriod>>(){{
            put("1", new ArrayList<BookingPeriod>() {{
                add(new BookingPeriod(java.time.LocalDate.parse("2021-02-01"), LocalDate.parse("2021-02-03")));
                add(new BookingPeriod(java.time.LocalDate.parse("2021-02-11"), LocalDate.parse("2021-02-11")));
            }});
            put("2", new ArrayList<BookingPeriod>() {{
                add(new BookingPeriod(java.time.LocalDate.parse("2021-02-01"), LocalDate.parse("2021-02-03")));
                add(new BookingPeriod(java.time.LocalDate.parse("2021-02-11"), LocalDate.parse("2021-02-11")));
            }});
        }};
    }
}