package com.tally.model.sync;

/**
 * Synchronous
 * description: Tasks A and B start together and end together.
 */
public class SyncParallelExecution {

    public static void main(String[] args) throws InterruptedException {
        final SyncParallelExecution instance = new SyncParallelExecution();

        Thread taskA = new Thread(instance::taskA);
        Thread taskB = new Thread(instance::taskB);

        final long start = System.currentTimeMillis();
        taskA.start(); // -> sleep(2)
        taskB.start(); // -> sleep(3)
        taskA.join(); // A 작업이 완료될 때까지 대기
        taskB.join(); // B 작업이 완료될 때까지 대기
        final long end = System.currentTimeMillis();

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
