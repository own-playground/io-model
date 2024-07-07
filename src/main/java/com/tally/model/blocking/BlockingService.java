package com.tally.model.blocking;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Synchronous & Blocking
 * description: 블록킹 방식이기 때문에 메인 스레드는 다른 작업을 수행할 수 없어서 작업이 완료될 때까지 기다려야 하고, 동기 방식이므로 작업이 완료되었을 때 즉시 결과를 반환한다.

 */
public class BlockingService {

    public static void main(String[] args) {
        BlockingService instance = new BlockingService();
        instance.syncBlocking();
    }

    private static final Map<Long, String> repository = new ConcurrentHashMap<>();

    static {
        repository.put(1L, "홍길동");
        repository.put(2L, "김철수");
        repository.put(3L, "이영희");
    }

    public void syncBlocking() {
        System.out.println("===== [Sync][Blocking] - 시작 =====");

        final String result = getById(2L);
        System.out.println("complete execute name = " + result);

        System.out.println("===== [Sync][Blocking] - 종료 =====");
    }

    private static String getById(final Long id) {
        sleep(1000);
        return repository.get(id);
    }

    private static void sleep(final int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
