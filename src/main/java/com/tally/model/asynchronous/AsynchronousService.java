package com.tally.model.asynchronous;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class AsynchronousService {

    private static final Map<Long, String> repository = new ConcurrentHashMap<>();

    static {
        repository.put(1L, "홍길동");
        repository.put(2L, "김철수");
        repository.put(3L, "이영희");
    }

    public static void main(String[] args) {
        AsynchronousService instance = new AsynchronousService();
        instance.execute();
    }

    public void execute() {
        System.out.println("Starting ");

        CompletableFuture<String> futureA = getById(2L, 2000); // 2초 후 완료
        CompletableFuture<String> futureB = getById(3L, 1000); // 1초 후 완료

        futureA.thenAccept(name -> {
            System.out.println("A 함수 완료: name = " + name);
        });

        futureB.thenAccept(result -> {
            System.out.println("B 함수 완료: name = " + result);
            System.out.println("=======  complete execute =======");
        });

        System.out.println("비동기 작업 시작");

        while (!futureA.isDone() || !futureB.isDone()) {
            if (!futureA.isDone()) {
                System.out.println("A 작업 진행 중...");
            }
            if (!futureB.isDone()) {
                System.out.println("B 작업 진행 중...");
            }
            try {
                Thread.sleep(100); // 짧은 시간 대기 후 다시 확인
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("모든 비동기 작업 완료");
    }

    /** 비동기 작업 A, B를 수행하는데 A가 먼저 끝나고 B가 다음에 끝남 */
    /**
     * public void execute() {
     *     System.out.println("Starting Case A");
     *
     *     CompletableFuture<String> futureA = getById(2L, 1000); // 1초 후 완료
     *     futureA.thenAccept(name -> {
     *         System.out.println("Case A - A 함수 완료: name = " + name);
     *         // A 함수 완료 후 B 함수 호출
     *         CompletableFuture<String> futureB = getById(3L, 2000); // 2초 후 완료
     *         futureB.thenAccept(result -> {
     *             System.out.println("Case A - B 함수 완료: name = " + result);
     *             System.out.println("======= Case A complete execute =======");
     *         });
     *     });
     *
     *     System.out.println("Case A - 비동기 작업 시작");
     * }
     */

    private CompletableFuture<String> getById(final Long id, int delay) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(delay); // I/O 작업 시뮬레이션
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return repository.get(id);
        });
    }
}
