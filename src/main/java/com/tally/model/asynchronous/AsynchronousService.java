package com.tally.model.asynchronous;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Asynchronous & Non-blocking
 * description: 비동기적으로 작업을 시작하고, 논블로킹 방식으로 결과를 기다린다.
 * 논블로킹 방식이기 때문에 메인 스레드는 다른 작업을 수행할 수 있고, 작업이 완료되었을 때 콜백을 통해 결과를 반환한다.
 */
public class AsynchronousService {

    public static void main(String[] args) {
        AsynchronousService instance = new AsynchronousService();
        instance.asyncNonBlocking();
    }

    private static final Map<Long, String> repository = new ConcurrentHashMap<>();

    static {
        repository.put(1L, "홍길동");
        repository.put(2L, "김철수");
        repository.put(3L, "이영희");
    }

    public void asyncNonBlocking() {
        System.out.println("===== [Async][Non-Blocking] - 시작 =====");

        CompletableFuture<String> futureA = CompletableFuture.supplyAsync(() -> {
            for (int i = 1; i <= 5; i++) {
                sleep(200);
                System.out.println("[비동기 작업] 작업A 진행중... " + i);
            }
            return repository.get(2L);  // 2초 후 완료
        });
        CompletableFuture<String> futureB = CompletableFuture.supplyAsync(() -> {
            for (int i = 1; i <= 5; i++) {
                sleep(100);
                System.out.println("[비동기 작업] 작업B 진행중... " + i);
            }
            return repository.get(3L);  // 1초 후 완료
        });

        CompletableFuture.allOf(futureA, futureB)
                .thenRun(() -> System.out.println("------ 모든 비동기 작업 완료 ------"))
                .thenCompose(v -> futureA).thenAccept(result -> System.out.println("complete futureA result = " + result))
                .thenCompose(v -> futureB).thenAccept(result ->System.out.println("complete futureB result = " + result));

        System.out.println("메인 스레드는 비동기 작업의 완료를 기다리지 않고 계속 실행됩니다.");
        for (int i = 1; i <= 5; i++) {
            sleep(300);
            System.out.println(">> [메인 스레드] 작업  진행중... " + i);
        }
        System.out.println("메인 스레드 작업 완료");

        System.out.println("===== [Async][Non-Blocking] - 종료 =====");
    }

    private static void sleep(final int millis) {
        try {
            Thread.sleep(millis); // 가상의 I/O 작업 시간
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
