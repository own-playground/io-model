package com.tally.model.blocking;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BlockingService {

    public static void main(String[] args) {
        BlockingService instance = new BlockingService();
        instance.blocking();
    }

    private static final Map<Long, String> repository = new ConcurrentHashMap<>();

    static {
        repository.put(1L, "홍길동");
        repository.put(2L, "김철수");
        repository.put(3L, "이영희");
    }

    public void blocking() {
        System.out.println("name = " + getById(2L));
        System.out.println("======= complete execute =======");
    }

    private static String getById(final Long id) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return repository.get(id);
    }
}
