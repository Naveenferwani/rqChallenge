package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.client.DummyEmployeeClient;
import com.example.rqchallenge.employees.client.model.DummyEmployeeListResponse;
import com.example.rqchallenge.employees.client.model.DummyEmployeeResponse;
import com.example.rqchallenge.employees.dto.Employee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final DummyEmployeeClient dummyEmployeeClient;

    /**
     * Method to get the all Employees
     * @return - List of Employees
     */
    public List<Employee> getAllEmployees() {
        log.debug("Calling Dummy Employee Client to get all Employees data");
        DummyEmployeeListResponse response = dummyEmployeeClient.getAllEmployees();
        log.debug("Successfully got the response from Dummy Employee Client to get all Employees data");
        return response.getData();
    }

    /**
     * Method to get Employees whose name matches the search string
     * @param searchString - Input search string
     * @return - List of Employees
     */
    public List<Employee> getEmployeesByNameSearch(String searchString) {
        log.debug("Calling Dummy Employee Client to get all Employees data");
        DummyEmployeeListResponse response = dummyEmployeeClient.getAllEmployees();
        log.debug("Successfully got the response from Dummy Employee Client to get all Employees data");
        List<Employee> result = new ArrayList<>();
        for (Employee employee : response.getData()) {
            if (employee.getEmployee_name().contains(searchString)) {
                result.add(employee);
            }
        }
        return result;
    }

    /**
     * Get Employee by Id
     *
     * @param id - Employee Id
     * @return - Employee record
     */
    public Employee getEmployeeById(String id) {
        DummyEmployeeResponse response = dummyEmployeeClient.getEmployee(id);
        return response.getData();
    }

    /**
     * Method to get Get Highest salary
     * Iterating over all the employees then comparing and getting the highest salary
     * @return - Highest salary integer
     */
    public Integer getHighestSalaryOfEmployees() {
        DummyEmployeeListResponse response = dummyEmployeeClient.getAllEmployees();
        int highest = 0;
        for (Employee employee : response.getData()) {
            if (employee.getEmployee_salary() > highest) {
                highest = (employee.getEmployee_salary());
            }
        }
        return highest;
    }

    /**
     * Method to get names of top 10 highest earning employees
     * Getting all the employees and then sorting the list based on salary in descending order.
     * Finally iterating the sorted the list to get the first 10 names
     *
     * @return - List of employee Names
     */
    public List<String> getTopTenHighestEarningEmployeeNames() {
        DummyEmployeeListResponse response = dummyEmployeeClient.getAllEmployees();
        List<Employee> employees = response.getData();
        employees.sort(Comparator.comparingInt(Employee::getEmployee_salary).reversed());

        List<String> names = new ArrayList<>();
        int count = 0;
        for (Employee employee : employees) {
            if (count == 10) {
                break;
            }
            names.add(employee.getEmployee_name());
            count++;
        }
        return names;
    }

    /**
     * Method to create an employee record
     *
     * @param employeeInput - Employee data
     * @return - Employee record with Id
     */
    public Employee createEmployee(Map<String, Object> employeeInput) {
        DummyEmployeeResponse dummyEmployeeResponse = dummyEmployeeClient.createEmployee(employeeInput);
        return dummyEmployeeResponse.getData();
    }

    /**
     * Method to delete an employee using id
     * @param id - Employee Id
     * @return - Employee Id
     */
    public String deleteEmployeeById(String id) {
        log.debug("Delete Employee {}", id);
        dummyEmployeeClient.deleteEmployee(id);
        log.debug("Successfully Deleted Employee {}", id);
        return id;
    }
}
