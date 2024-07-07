package com.tally.model.nonblocking;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class NonBlockingService {

    public static void main(String[] args) {
        NonBlockingService instance = new NonBlockingService();
        instance.nonBlocking();
    }

    private static final Map<Long, String> repository = new ConcurrentHashMap<>();

    static {
        repository.put(1L, "홍길동");
        repository.put(2L, "김철수");
        repository.put(3L, "이영희");
    }

    public void nonBlocking() {
        CompletableFuture<String> future = getById(1L);

        // 비동기 작업 상태를 지속적으로 확인
        while (!future.isDone()) {
            System.out.println("작업 진행 중...");
            try {
                Thread.sleep(100); // 짧은 시간 대기 후 다시 확인
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 작업이 완료되었을 때
        future.thenAccept(name -> {
            System.out.println("name = " + name);
            System.out.println("======= complete execute =======");
        });

    }

    private static CompletableFuture<String> getById(final Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000); // I/O 작업 시뮬레이션
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return repository.get(id);
        });
    }
}
