package com.javatechie.employeereminderservice;

import com.javatechie.database.EmployeeDataBase;
import com.javatechie.dto.Employee;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class EmployeeReminderService {

    public void sendReminderToEmployees() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> voidCompletableFuture =CompletableFuture.supplyAsync(()-> {
            System.out.println("Fetch Employee Thread:"+Thread.currentThread().getName());
            try {
                return EmployeeDataBase.fetchEmployees();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).thenApplyAsync((employees)->{
            System.out.println("Fetch only new Employee Thread:"+Thread.currentThread().getName());
            return employees
                    .stream()
                    .filter(e->e.getNewJoiner().equals("TRUE"))
                    .collect(Collectors.toList());
        }).thenApplyAsync((newJoinee)->{
            System.out.println("Thread that Checks if the new employee Completed the training:"
                    +Thread.currentThread().getName());
            return newJoinee
                    .stream()
                    .filter(e->e.getLearningPending().equals("TRUE"))
                    .collect(Collectors.toList());
        }).thenApplyAsync((employeeNotCompletedTrainings)->{

            System.out.println("Thread that fetch email id of new employee who did not complete the training:"
                    +Thread.currentThread().getName());

            return employeeNotCompletedTrainings.stream()
                    .map(Employee::getEmail)
                    .collect(Collectors.toList());
        }).thenAcceptAsync(emails->{
            System.out.println("Thread that sends reminder email to new employee:"
                    +Thread.currentThread().getName());
            emails.forEach(System.out::println);
        });
        voidCompletableFuture.get();
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        EmployeeReminderService service = new EmployeeReminderService();
        service.sendReminderToEmployees();
    }
}
