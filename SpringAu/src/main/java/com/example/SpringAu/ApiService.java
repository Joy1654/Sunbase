package com.example.SpringAu;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

public class ApiService {
    private final String BASE_URL = "https://qa2.sunbasedata.com/sunbase/portal/api";
    private RestTemplate restTemplate = null;

    public ApiService() {
        this.restTemplate = restTemplate;
    }
    public boolean updateCustomer(
            String uuid,
            String firstName,
            String lastName,
            String street,
            String address,
            String city,
            String state,
            String email,
            String phone
    ) {
        String token = getBearerToken("your_login_id", "your_password"); // Replace with your actual login credentials
        if (token == null) {
            // Handle error, return false or throw an exception
            return false;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Prepare the customer data in a JSON format
        Customer updatedCustomer = new Customer(firstName, lastName, street, address, city, state, email, phone);

        HttpEntity<Customer> requestEntity = new HttpEntity<>(updatedCustomer, headers);

        String apiUrl = BASE_URL + "/assignment.jsp?cmd=update&uuid=" + uuid;

        ResponseEntity<String> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        return response.getStatusCode().is2xxSuccessful();
    }

    public Customer getCustomerByUuid(String uuid) {
        String token = getBearerToken("your_login_id", "your_password"); // Replace with your actual login credentials
        if (token == null) {
            // Handle error, return null or throw an exception
            return null;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String apiUrl = BASE_URL + "/assignment.jsp";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("cmd", "get_customer")
                .queryParam("uuid", uuid);

        ResponseEntity<Customer> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                requestEntity,
                Customer.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            return null; // Handle error, return null or throw an exception
        }
    }
    public ResponseEntity<String> createCustomer(Customer customer) {
        String token = getBearerToken("your_login_id", "your_password"); // Replace with your actual login credentials
        if (token == null) {
            // Handle error, return ResponseEntity with appropriate error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to obtain token");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Customer> requestEntity = new HttpEntity<>(customer, headers);

        String apiUrl = BASE_URL + "/assignment.jsp?cmd=create";
        ResponseEntity<String> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        return response;
    }

    public String getBearerToken(String loginId, String password) {
        // Implement API call to obtain bearer token
        String tokenUrl = BASE_URL + "/assignment_auth.jsp";

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(loginId, password);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                tokenUrl,
                HttpMethod.POST,
                entity,
                String.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            return null; // Handle error
        }
    }

    public List<Customer> getCustomerList(String token) {
        String apiUrl = BASE_URL + "/assignment.jsp";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("cmd", "get_customer_list");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Customer>> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Customer>>() {} // Use ParameterizedTypeReference for generic types
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            return Collections.emptyList(); // Handle error
        }
    }

    public boolean deleteCustomer(String uuid) {
        String token = getBearerToken("your_login_id", "your_password"); // Replace with your actual login credentials
        if (token == null) {
            // Handle error, return false or throw an exception
            return false;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String apiUrl = BASE_URL + "/assignment.jsp";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("cmd", "delete")
                .queryParam("uuid", uuid);

        ResponseEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        return response.getStatusCode().is2xxSuccessful();}

}
