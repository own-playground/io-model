package com.tally.model.group.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Asynchronous
 * description: Task A runs first, then Task B, then Task A ends, then Task B ends
 */
public class AsyncSecondTaskFinishLateExecution {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        AsyncSecondTaskFinishLateExecution instance = new AsyncSecondTaskFinishLateExecution();

        long start = System.currentTimeMillis();
        CompletableFuture.allOf(
                CompletableFuture.runAsync(instance::taskA), // -> sleep(2000)
                CompletableFuture.runAsync(instance::taskB)  // -> sleep(3000)
        ).get();
        long end = System.currentTimeMillis();

        System.out.println("All tasks finished execute time = " + (end - start));
    }

    public void taskA() {
        System.out.println("Task A started");
        try {
            Thread.sleep(2000); // Simulate task taking 2 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Task A finished");
    }

    public void taskB() {
        System.out.println("Task B started");
        try {
            Thread.sleep(3000); // Simulate task taking 3 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Task B finished");
    }
}