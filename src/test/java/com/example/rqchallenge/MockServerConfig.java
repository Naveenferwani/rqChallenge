package com.example.rqchallenge;

import org.apache.commons.io.FileUtils;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.MediaType;
import org.mockserver.verify.VerificationTimes;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class MockServerConfig {

    private static final Integer MOCK_SERVER_PORT = 8080;
    private final ClientAndServer clientAndServer;
    private final MockServerClient mockServerClient = new MockServerClient("localhost", MOCK_SERVER_PORT);

    MockServerConfig() {
        this.clientAndServer = ClientAndServer.startClientAndServer(MOCK_SERVER_PORT);
    }

    public void registerGetAllEmployeesEndpoint() throws IOException {
        String response = FileUtils.readFileToString(new File("src/test/resources/employees.json"), StandardCharsets.UTF_8);
        mockServerClient
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/dummy/api/v1/employees"),
                        exactly(4)
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withContentType(MediaType.APPLICATION_JSON)
                                .withBody(response)
                                .withDelay(TimeUnit.SECONDS, 1)
                );
    }

    public void registerGetEmployeeEndpoint() throws IOException {
        String response = FileUtils.readFileToString(new File("src/test/resources/single_employee.json"), StandardCharsets.UTF_8);
        mockServerClient
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/dummy/api/v1/employees/1"),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withContentType(MediaType.APPLICATION_JSON)
                                .withBody(response)
                                .withDelay(TimeUnit.SECONDS, 1)
                );
    }

    public void registerCreateEmployeeEndpoint() throws IOException {
        String response = FileUtils.readFileToString(new File("src/test/resources/single_employee.json"), StandardCharsets.UTF_8);
        mockServerClient
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/dummy/api/v1/employees"),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withContentType(MediaType.APPLICATION_JSON)
                                .withBody(response)
                                .withDelay(TimeUnit.SECONDS, 1)
                );
    }

    public void registerDeleteEmployeeEndpoint() throws IOException {
        String response = FileUtils.readFileToString(new File("src/test/resources/single_employee.json"), StandardCharsets.UTF_8);
        mockServerClient
                .when(
                        request()
                                .withMethod("DELETE")
                                .withPath("/dummy/api/v1/employees/1"),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withContentType(MediaType.APPLICATION_JSON)
                                .withBody(response)
                                .withDelay(TimeUnit.SECONDS, 1)
                );
    }
    public void stopServer() {
        clientAndServer.stop();
    }
}
