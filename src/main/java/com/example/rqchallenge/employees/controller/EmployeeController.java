package com.example.rqchallenge.employees.controller;

import com.example.rqchallenge.employees.dto.Employee;
import com.example.rqchallenge.employees.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController implements IEmployeeController{

    private final EmployeeService employeeService;

    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(@PathVariable String searchString) {
        return ResponseEntity.ok(employeeService.getEmployeesByNameSearch(searchString));
    }

    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {

        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {

        return ResponseEntity.ok(employeeService.getHighestSalaryOfEmployees());
    }

    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {

        return ResponseEntity.ok(employeeService.getTopTenHighestEarningEmployeeNames());
    }

    public ResponseEntity<Employee> createEmployee(Map<String, Object> employeeInput) {
        return ResponseEntity.ok(employeeService.createEmployee(employeeInput));
    }

    public ResponseEntity<String> deleteEmployeeById(String id) {
        return ResponseEntity.ok(employeeService.deleteEmployeeById(id));
    }
}
