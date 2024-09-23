package com.example.rqchallenge.employees.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class Employee {
    Integer id;
    String employee_name;
    Integer employee_salary;
    Integer employee_age;
    String profile_image;
}
