package com.example.rqchallenge.employees.client;

import com.example.rqchallenge.employees.client.model.DummyEmployeeListResponse;
import com.example.rqchallenge.employees.client.model.DummyEmployeeResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class DummyEmployeeClient {
    private static final String URL = "http://localhost:8080/dummy/api/v1/employees";
    private final RestTemplate restTemplate;

    public DummyEmployeeClient(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    /**
     * Method to get All Employees from Dummy Employee endpoint
     * @return - Employee List Response
     */
    public DummyEmployeeListResponse getAllEmployees() {
        return restTemplate.getForEntity(URL, DummyEmployeeListResponse.class).getBody();
    }

    /**
     * Get Employee using ID from Dummy Employee Endpoint
     * @param id - Employee Id
     * @return - Employee Response
     */
    public DummyEmployeeResponse getEmployee(String id) {
        return restTemplate.getForEntity(URL + "/" + id, DummyEmployeeResponse.class).getBody();
    }

    /**
     * Create an employee record
     * @param body - Employee data
     *
     * @return - Employee Response
     */
    public DummyEmployeeResponse createEmployee(Map<String, Object> body) {
        return restTemplate.postForObject(URL, body, DummyEmployeeResponse.class);
    }

    /**
     * Delete Employee by Id
     * @param id - Employee Id
     */
    public void deleteEmployee(String id) {
        restTemplate.delete(URL + "/" + id);
    }
}
