package com.tally.model.group.sync;

/**
 * Synchronous
 * description: Task A starts alone and ends alone, and Task B also starts alone and ends alone.
 */
public class SyncSingleExecution {

    public static void main(String[] args) {
        final SyncSingleExecution instance = new SyncSingleExecution();

        final long start = System.currentTimeMillis();
        instance.taskA(); // A 함수 호출
        instance.taskB(); // B 함수 호출 (A 함수가 종료된 후)
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
