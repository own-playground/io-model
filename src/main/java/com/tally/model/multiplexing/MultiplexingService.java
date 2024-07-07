package com.tally.model.multiplexing;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

/**
 * Asynchronous & Blocking
 * description: 비동기적으로 작업을 시작하지만, 블로킹 방식으로 결과를 기다린다.
 * 블록킹 방식이기 메인 스레드는 비동기 작업이 완료될 때까지 다른 작업을 수행할 수 없고, 작업이 완료되었을 때 즉시 결과를 반환한다.
 *
 * 비동기 작업 자체는 완료 시점을 예측할 수 없지만, 블로킹 호출(future.get())로 인해 결과를 즉시 받을 수 있음
 */
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
        System.out.println("===== [비동기][블록킹] - 시작 =====");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            for (int i = 1; i <= 5; i++) {
                sleep(200);
                System.out.println("비동기 작업 진행 중... " + i);
            }
            return repository.get(2L);
        });

        System.out.println("비동기 작업 시작.");
        System.out.println("메인 스레드는 결과를 기다리는 동안 블로킹됩니다.");

        try {
            final String result = future.get(); // 비동기 작업이 완료될 때까지 블로킹

            System.out.println("complete execute name = " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("===== [비동기][블록킹] - 종료 =====");
    }

    private static void sleep(final int millis) {
        try {
            Thread.sleep(millis); // 가상의 I/O 작업 시간
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
