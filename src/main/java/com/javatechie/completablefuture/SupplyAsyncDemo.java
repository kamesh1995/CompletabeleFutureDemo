package com.javatechie.completablefuture;

import com.javatechie.database.EmployeeDataBase;
import com.javatechie.dto.Employee;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SupplyAsyncDemo {

    public List<Employee> fetchEmployees() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Employee>> future =
                CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("Thread:"+Thread.currentThread().getName());
                    try {
                        return EmployeeDataBase.fetchEmployees();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }, Executors.newFixedThreadPool(3)
        );
      return future.get();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SupplyAsyncDemo supplyAsyncDemo = new SupplyAsyncDemo();
        supplyAsyncDemo.fetchEmployees();
    }
}
