package com.javatechie.completablefuture;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class WhyNotCompletableFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);
        Future<List<Integer>> futureList = service.submit(
                                           ()-> Arrays.asList(1,2,3));
        List<Integer> list = futureList.get();
        //System.out.println(list);

        //create instance of CompletableFuture
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        completableFuture.get();
        completableFuture.complete("return some dummy value");

    }
}
