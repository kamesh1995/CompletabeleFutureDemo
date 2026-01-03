package com.javatechie.database;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatechie.dto.Employee;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class EmployeeDataBase {
    public  static List<Employee> fetchEmployees() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Employee> employees = mapper.readValue(new File("employee.json"), new TypeReference<List<Employee>>(){});
        return employees;
    }
}
