package com.example.rqchallenge.employees.client;

import com.example.rqchallenge.employees.client.model.DummyEmployeeListResponse;
import com.example.rqchallenge.employees.client.model.DummyEmployeeResponse;
import com.example.rqchallenge.employees.dto.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DummyEmployeeClientTest {

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private RestTemplateBuilder restTemplateBuilder;
    private DummyEmployeeClient dummyEmployeeClient;
    private final List<Employee> employees = new ArrayList<>();

    private static final String URL = "http://localhost:8080/dummy/api/v1/employees";

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        when(restTemplateBuilder.build()).thenReturn(restTemplate);
        dummyEmployeeClient = new DummyEmployeeClient(restTemplateBuilder);
        employees.add(Employee.builder().id(1).employee_name("Test 1").build());
        employees.add(Employee.builder().id(2).employee_name("Test 2").build());
        employees.add(Employee.builder().id(3).employee_name("Test 3").build());
    }

    @Test
    void getAllEmployees_success() {
        when(restTemplate.getForEntity(URL, DummyEmployeeListResponse.class))
                .thenReturn(ResponseEntity.ok(DummyEmployeeListResponse.builder().data(employees).build()));
        DummyEmployeeListResponse response = dummyEmployeeClient.getAllEmployees();
        assertEquals(3, response.getData().size());
        verify(restTemplate, times(1)).getForEntity(URL, DummyEmployeeListResponse.class);
    }

    @Test
    void getEmployee_success() {
        when(restTemplate.getForEntity(URL+"/"+1, DummyEmployeeResponse.class))
                .thenReturn(ResponseEntity.ok(DummyEmployeeResponse.builder().data(employees.get(0)).build()));
        DummyEmployeeResponse response = dummyEmployeeClient.getEmployee("1");
        assertEquals(1, response.getData().getId());
        verify(restTemplate, times(1)).getForEntity(URL+"/"+1, DummyEmployeeResponse.class);
    }

    @Test
    void createEmployee_success() {
        Map<String, Object> body = new HashMap<>();
        body.put("name", "Test 1");
        when(restTemplate.postForObject(URL, body, DummyEmployeeResponse.class))
                .thenReturn(DummyEmployeeResponse.builder().data(employees.get(0)).build());
        DummyEmployeeResponse response = dummyEmployeeClient.createEmployee(body);
        assertEquals(1, response.getData().getId());
        verify(restTemplate, times(1)).postForObject(URL, body, DummyEmployeeResponse.class);

    }

    @Test
    void deleteEmployee_success() {
        doNothing().when(restTemplate).delete(URL+"/"+1);
        dummyEmployeeClient.deleteEmployee("1");
        verify(restTemplate, times(1)).delete(URL+"/"+1);
    }
}