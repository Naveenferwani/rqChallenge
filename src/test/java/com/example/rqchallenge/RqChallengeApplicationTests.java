package com.example.rqchallenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
class RqChallengeApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    private static MockServerConfig mockServerConfig;

    @BeforeAll
    static void startServer() throws IOException {
        mockServerConfig = new MockServerConfig();
        mockServerConfig.registerGetAllEmployeesEndpoint();
        mockServerConfig.registerGetEmployeeEndpoint();
        mockServerConfig.registerCreateEmployeeEndpoint();
        mockServerConfig.registerDeleteEmployeeEndpoint();
    }

    @Test
    void getAllEmployees_success() throws Exception {
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/employees"));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                        Is.is(12)));
    }

    @Test
    void getEmployeesByNameSearch_success() throws Exception {
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/employees/search/Employee 1"));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                        Is.is(4)));
    }

    @Test
    void getEmployeeById_success() throws Exception {
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/employees/1"));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.employee_name",
                        Is.is("Joe Bloggs")));
    }

    @Test
    void getHighestSalaryOfEmployees_success() throws Exception {
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/employees/highestSalary"));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Is.is(137090)));
    }

    @Test
    void getTopTenHighestEarningEmployeeNames_success() throws Exception {
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/employees/topTenHighestEarningEmployeeNames"));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                        Is.is(10)));
    }

    @Test
    void createEmployee_success() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("name", "Test 1");
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
        ResultActions response = mockMvc
                .perform(MockMvcRequestBuilders.delete("/employees/1"));
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @AfterAll
    public static void stopServer() {
        mockServerConfig.stopServer();
    }

}
