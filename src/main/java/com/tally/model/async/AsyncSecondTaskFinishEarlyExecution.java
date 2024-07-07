package com.tally.model.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Asynchronous
 * description: Task A runs first, then Task B, but Task B finishes first and Task A ends
 */
public class AsyncSecondTaskFinishEarlyExecution {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        AsyncSecondTaskFinishEarlyExecution instance = new AsyncSecondTaskFinishEarlyExecution();

        long start = System.currentTimeMillis();
        CompletableFuture.allOf(
                CompletableFuture.runAsync(instance::taskA), // -> sleep(3000)
                CompletableFuture.runAsync(instance::taskB)  // -> sleep(1000)
        ).get();
        long end = System.currentTimeMillis();

        System.out.println("All tasks finished execute time = " + (end - start));
    }

    public void taskA() {
        System.out.println("Task A started");
        try {
            Thread.sleep(3000); // Simulate task taking 3 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Task A finished");
    }

    public void taskB() {
        System.out.println("Task B started");
        try {
            Thread.sleep(1000); // Simulate task taking 1 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Task B finished");
    }
}