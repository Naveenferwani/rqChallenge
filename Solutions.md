## Coding Challenge Solution

1. Added a Dummy Employee Controller with the below APIs
    a) GET all Employees - localhost:8080/dummy/api/v1/employees - GET
    b) Get an employee - localhost:8080/dummy/api/v1/employees/1 - GET
    c) Delete an employee - localhost:8080/dummy/api/v1/employees/1 - DELETE
    d) Create an employee - localhost:8080/dummy/api/v1/employees - POST
    {
        "name": "ABC",
        "age": 20,
        "salary": 50000
    }
2. Created a client which call the above dummy endpoint using restTemplate - DummyEmployeeClient.
3. Created a service class which has the logic to call the DummyEmployeeClient and also to manipulate data as per need - Get highest salary, Top 10 Highest salary.
4. Implemented IEmployeeController to create/implement all the endpoints - EmployeeController class
5. Added a postman collection for all the 7 endpoints - postman/EmployeeCollection.postman_collection.json
6. Added happy path unit tests for all the classes.
7. Added happy path integration tests for all the endpoints.
8. Added Loggers

#### Things to Do

Below are the things I would have done if got some more time

1. Implement Failure Scenarios
2. Unit tests for negative cases
3. Integration Tests
4. Securing API - Authentication