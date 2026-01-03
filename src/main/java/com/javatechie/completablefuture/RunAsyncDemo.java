package com.javatechie.completablefuture;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatechie.dto.Employee;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RunAsyncDemo {

    public void saveEmployee(File jsonFile) throws ExecutionException, InterruptedException {

        ObjectMapper mapper = new ObjectMapper();
        CompletableFuture<Void> future = CompletableFuture.runAsync(
                ()->{
                    try {
                        List<Employee> employees = mapper.readValue(jsonFile, new TypeReference<List<Employee>>(){});

                        //save employees data
                        //repositories.saveAll(employees);
                        System.out.println("Thread:"+Thread.currentThread().getName());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        future.get();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        RunAsyncDemo demo = new RunAsyncDemo();
        demo.saveEmployee(new File("employee.json"));
    }
}
