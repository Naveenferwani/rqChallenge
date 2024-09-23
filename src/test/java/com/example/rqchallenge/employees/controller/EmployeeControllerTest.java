package com.example.rqchallenge.employees.controller;

import com.example.rqchallenge.employees.dto.Employee;
import com.example.rqchallenge.employees.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;
    private final List<Employee> employees = new ArrayList<>();

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        EmployeeController employeeController = new EmployeeController(employeeService);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        employees.add(Employee.builder().id(1).employee_name("Joe Bloggs").employee_salary(100).build());
        employees.add(Employee.builder().id(2).employee_name("John Cena").employee_salary(200).build());
        employees.add(Employee.builder().id(3).employee_name("Undertaker").employee_salary(350).build());
    }

    @Test
    void getAllEmployees_success() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(employees);
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/employees"));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                        Is.is(employees.size())));
    }

    @Test
    void getEmployeesByNameSearch_success() throws Exception {
        when(employeeService.getEmployeesByNameSearch("Jo")).thenReturn(employees.subList(0, 2));
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/employees/search/Jo"));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                        Is.is(2)));
    }

    @Test
    void getEmployeeById_success() throws Exception {
        when(employeeService.getEmployeeById("1")).thenReturn(employees.get(0));
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/employees/1"));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.employee_name",
                        Is.is("Joe Bloggs")));
    }

    @Test
    void getHighestSalaryOfEmployees_success() throws Exception {
        when(employeeService.getHighestSalaryOfEmployees()).thenReturn(1000);
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/employees/highestSalary"));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Is.is(1000)));
    }

    @Test
    void getTopTenHighestEarningEmployeeNames_success() throws Exception {
        when(employeeService.getTopTenHighestEarningEmployeeNames()).thenReturn(List.of("ABC", "DEF"));
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/employees/topTenHighestEarningEmployeeNames"));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                        Is.is(2)));
    }

    @Test
    void createEmployee_success() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("name", "Test 1");
        when(employeeService.createEmployee(body)).thenReturn(employees.get(0));
        ResultActions response = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/employees")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(body))
                );
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id",
                        Is.is(1)));
    }


    @Test
    void deleteEmployeeById_success() throws Exception {
        when(employeeService.deleteEmployeeById("1")).thenReturn("1");
        ResultActions response = mockMvc
                .perform(MockMvcRequestBuilders.delete("/employees/1"));
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}