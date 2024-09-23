package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.client.DummyEmployeeClient;
import com.example.rqchallenge.employees.client.model.DummyEmployeeListResponse;
import com.example.rqchallenge.employees.client.model.DummyEmployeeResponse;
import com.example.rqchallenge.employees.dto.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private DummyEmployeeClient dummyEmployeeClient;
    private EmployeeService employeeService;
    private final List<Employee> employees = new ArrayList<>();

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        employeeService = new EmployeeService(dummyEmployeeClient);
        employees.add(Employee.builder().id(1).employee_name("Joe Bloggs").employee_salary(100).build());
        employees.add(Employee.builder().id(2).employee_name("John Cena").employee_salary(200).build());
        employees.add(Employee.builder().id(3).employee_name("Undertaker").employee_salary(350).build());
    }

    @Test
    void getAllEmployees_success() {
        when(dummyEmployeeClient.getAllEmployees()).thenReturn(DummyEmployeeListResponse.builder()
                .data(employees).build());
        List<Employee> employeeList = employeeService.getAllEmployees();
        assertEquals(employees.size(), employeeList.size());
        verify(dummyEmployeeClient, times(1)).getAllEmployees();
    }

    @Test
    void getEmployeesByNameSearch_success() {
        when(dummyEmployeeClient.getAllEmployees()).thenReturn(DummyEmployeeListResponse.builder()
                .data(employees).build());
        List<Employee> employeeList = employeeService.getEmployeesByNameSearch("Jo");
        assertEquals(2, employeeList.size());
        assertEquals("Joe Bloggs", employeeList.get(0).getEmployee_name());

        employeeList = employeeService.getEmployeesByNameSearch("taker");
        assertEquals(1, employeeList.size());
        assertEquals("Undertaker", employeeList.get(0).getEmployee_name());
        verify(dummyEmployeeClient, times(2)).getAllEmployees();

    }

    @Test
    void getEmployeeById() {
        when(dummyEmployeeClient.getEmployee("1")).thenReturn(DummyEmployeeResponse.builder()
                .data(employees.get(0)).build());

        Employee employee = employeeService.getEmployeeById("1");
        assertEquals(1, employee.getId());
        verify(dummyEmployeeClient, times(1)).getEmployee("1");
    }

    @Test
    void getHighestSalaryOfEmployees() {
        when(dummyEmployeeClient.getAllEmployees()).thenReturn(DummyEmployeeListResponse.builder()
                .data(employees).build());
        Integer highestSalary = employeeService.getHighestSalaryOfEmployees();
        assertEquals(350, highestSalary);
        verify(dummyEmployeeClient, times(1)).getAllEmployees();
    }

    @Test
    void getTopTenHighestEarningEmployeeNames() {
        when(dummyEmployeeClient.getAllEmployees()).thenReturn(DummyEmployeeListResponse.builder()
                .data(employees).build());
        List<String> names = employeeService.getTopTenHighestEarningEmployeeNames();
        assertEquals(3, names.size());

        employees.add(Employee.builder().id(4).employee_name("Joe Bloggs 2").employee_salary(110).build());
        employees.add(Employee.builder().id(5).employee_name("John Cena2").employee_salary(210).build());
        employees.add(Employee.builder().id(6).employee_name("Undertaker 2").employee_salary(360).build());
        employees.add(Employee.builder().id(7).employee_name("Joe Bloggs 4").employee_salary(120).build());
        employees.add(Employee.builder().id(8).employee_name("John Cena 4").employee_salary(220).build());
        employees.add(Employee.builder().id(9).employee_name("Undertaker 4").employee_salary(360).build());
        employees.add(Employee.builder().id(10).employee_name("Joe Bloggs 5").employee_salary(130).build());
        employees.add(Employee.builder().id(11).employee_name("John Cena 6").employee_salary(240).build());
        employees.add(Employee.builder().id(12).employee_name("Undertaker 3").employee_salary(380).build());

        when(dummyEmployeeClient.getAllEmployees()).thenReturn(DummyEmployeeListResponse.builder()
                .data(employees).build());
        names = employeeService.getTopTenHighestEarningEmployeeNames();
        assertEquals(10, names.size());
        assertTrue(names.contains("John Cena 4"));
        verify(dummyEmployeeClient, times(2)).getAllEmployees();
    }

    @Test
    void createEmployee() {
        Map<String, Object> body = new HashMap<>();
        body.put("name", "Test 1");
        when(dummyEmployeeClient.createEmployee(body)).thenReturn(DummyEmployeeResponse.builder()
                .data(employees.get(0)).build());
        Employee employee = employeeService.createEmployee(body);
        assertNotNull(employee);
        verify(dummyEmployeeClient, times(1)).createEmployee(body);
    }

    @Test
    void deleteEmployeeById() {
        doNothing().when(dummyEmployeeClient).deleteEmployee("1");
        employeeService.deleteEmployeeById("1");
        verify(dummyEmployeeClient, times(1)).deleteEmployee("1");
    }
}