package com.tally.model.multiplexing;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

public class MultiplexingService {

    public static void main(String[] args) {
        MultiplexingService instance = new MultiplexingService();
        instance.asyncBlocking();
    }

    private static final Map<Long, String> repository = new ConcurrentHashMap<>();

    static {
        repository.put(1L, "홍길동");
        repository.put(2L, "김철수");
        repository.put(3L, "이영희");
    }

    public void asyncBlocking() {
        CompletableFuture<String> future = getById(2L);

        try {
            // 비동기 작업이 완료될 때까지 블로킹
            String name = future.get();
            System.out.println("name = " + name);
            System.out.println("======= complete execute =======");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
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
