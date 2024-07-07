package com.tally.model.nonblocking;

import java.util.Map;
import java.util.concurrent.*;

/**
 * Synchronous & Non-blocking
 * description: 논블럭킹 방식이기 때문에 메인 스레드는 다른 작업을 수행할 수 있는 대신 동기 방식이므로 작업이 완료되었는지 확인하고 완료되었다면 즉시 결과를 반환한다.
 */
public class NonBlockingService {

    public static void main(String[] args) {
        NonBlockingService instance = new NonBlockingService();
        instance.SyncNonBlocking();
    }

    private static final Map<Long, String> repository = new ConcurrentHashMap<>();

    static {
        repository.put(1L, "홍길동");
        repository.put(2L, "김철수");
        repository.put(3L, "이영희");
    }

    public void SyncNonBlocking() {
        System.out.println("===== [Sync][Non-Blocking] - 시작 =====");

        final ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            Future<String> future = executor.submit(() -> {
                sleep(500); // 가상의 I/O 작업 시간
                return repository.get(1L);
            });

            for (int i = 1; i <= 50; i++) {
                if(future.isDone()) {
                    break;
                }
                System.out.println("메인 스레드는 다른 작업을 수행할 수 있음: " + i);
                sleep(100);
            }

            String result = future.get(); // 이미 완료되었으므로 즉시 반환
            System.out.println("complete execute name = " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
            System.out.println("===== [Sync][Non-Blocking] - 종료 =====");
        }
    }

    private static void sleep(final int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
