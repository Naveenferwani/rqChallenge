package com.example.rqchallenge.employees.client.model;

import com.example.rqchallenge.employees.dto.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class DummyEmployeeListResponse {
    String status;
    List<Employee> data;
}
