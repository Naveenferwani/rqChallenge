package com.example.rqchallenge.employees.dummy;

import com.example.rqchallenge.employees.dto.Employee;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/dummy/api/v1/employees")
public class DummyEmployeeController {

    private static final Map<Integer, Employee> EMPLOYEES;
    private static Integer count = 0;

    static {
        EMPLOYEES = new HashMap<>();
        EMPLOYEES.put(1, createEmployee(1, "Test Employee 1", 100000,
                45, "Test1.png"));
        EMPLOYEES.put(2, createEmployee(2, "Test Employee 2", 50000,
                40, "Test2.png"));
        EMPLOYEES.put(3, createEmployee(3, "Test Employee 3", 120000,
                30, "Test3.png"));
        EMPLOYEES.put(4, createEmployee(4, "Test Employee 4", 130000,
                50, "Test4.png"));
        EMPLOYEES.put(5, createEmployee(5, "Test Employee 5", 18000,
                65, "Test5.png"));
        EMPLOYEES.put(6, createEmployee(6, "Test Employee 6", 10000,
                35, "Test6.png"));
        EMPLOYEES.put(7, createEmployee(7, "Test Employee 7", 130080,
                38, "Test7.png"));
        EMPLOYEES.put(8, createEmployee(8, "Test Employee 8", 110000,
                39, "Test8.png"));
        EMPLOYEES.put(9, createEmployee(9, "Test Employee 9", 135000,
                47, "Test9.png"));
        EMPLOYEES.put(10, createEmployee(10, "Test Employee 10", 135090,
                42, "Test10.png"));
        EMPLOYEES.put(11, createEmployee(11, "Test Employee 11", 137090,
                42, "Test10.png"));
        EMPLOYEES.put(12, createEmployee(12, "Test Employee 12", 136090,
                41, "Test10.png"));
        count = 12;

    }

    private static Employee createEmployee(Integer id, String employeeName, Integer employeeSalary, Integer employeeAge,
                                    String profileImage) {
        return Employee.builder()
                .employee_name(employeeName)
                .employee_age(employeeAge)
                .employee_salary(employeeSalary)
                .id(id)
                .profile_image(profileImage)
                .build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getAllDummyEmployees() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", EMPLOYEES.values());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getDummyEmployee(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        Employee employee = EMPLOYEES.get(id);
        if(employee == null) {
            response.put("status", "No Employee Found");
        } else {
            response.put("status", "success");
        }
        response.put("data", employee);

        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<Map<String, Object>> createEmployee(@RequestBody Map<String, String> body) {
        Map<String, Object> response = new HashMap<>();
        count++;
        Employee employee = createEmployee(count, body.get("name"), Integer.parseInt(body.get("salary")), Integer.parseInt(body.get("age")), body.get("image"));
        EMPLOYEES.put(count, employee);
        response.put("status", "success");
        response.put("data", employee);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteEmployee(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        Employee employee = EMPLOYEES.remove(id);
        if(employee == null) {
            response.put("message", "No Record found");
        } else {
            response.put("message", "successfully! deleted Record");
        }
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }


}
