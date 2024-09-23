package com.example.rqchallenge.employees.client.model;

import com.example.rqchallenge.employees.dto.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class DummyEmployeeResponse {

    String status;
    Employee data;
}
