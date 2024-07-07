# I/O Models

- Synchronous I/O
  - Blocking: Blocking I/O
  - Non-blocking: Non-blocking I/O
- Asynchronous I/O
  - Blocking: I/O Multiplexing(`select`, `poll`, `epoll`)
  - Non-blocking: Asynchronous I/O(a.k.a. AIO)

---

### Synchrounous & Blocking

```java
===== [Sync][Blocking] - 시작 =====
complete execute name = 김철수
===== [Sync][Blocking] - 종료 =====
```

### Synchrounous & Non-blocking

```java
===== [Sync][Non-Blocking] - 시작 =====
[polling - 1] 메인 스레드는 다른 작업을 수행할 수 있음
[비동기 작업] 작업 진행 중... 1
[polling - 2] 메인 스레드는 다른 작업을 수행할 수 있음
[polling - 3] 메인 스레드는 다른 작업을 수행할 수 있음
[비동기 작업] 작업 진행 중... 2
[polling - 4] 메인 스레드는 다른 작업을 수행할 수 있음
[비동기 작업] 작업 진행 중... 3
[polling - 5] 메인 스레드는 다른 작업을 수행할 수 있음
[비동기 작업] 작업 진행 중... 4
[비동기 작업] 작업 진행 중... 5
[polling - 6] 메인 스레드는 다른 작업을 수행할 수 있음
complete execute name = 홍길동
===== [Sync][Non-Blocking] - 종료 =====
```

### Asynchronous & Blocking

```java
===== [Async][Blocking] - 시작 =====
비동기 작업 시작.
메인 스레드는 결과를 기다리는 동안 블로킹됩니다.
비동기 작업 진행 중... 1
비동기 작업 진행 중... 2
비동기 작업 진행 중... 3
비동기 작업 진행 중... 4
비동기 작업 진행 중... 5
complete execute name = 김철수
===== [Async][Blocking] - 종료 =====
```

### Asynchronous & Non-blocking

```java
===== [Async][Non-Blocking] - 시작 =====
메인 스레드는 비동기 작업의 완료를 기다리지 않고 계속 실행됩니다.
[비동기 작업] 작업B 진행중... 1
[비동기 작업] 작업A 진행중... 1
[비동기 작업] 작업B 진행중... 2
>> [메인 스레드] 작업  진행중... 1
[비동기 작업] 작업B 진행중... 3
[비동기 작업] 작업A 진행중... 2
[비동기 작업] 작업B 진행중... 4
[비동기 작업] 작업B 진행중... 5
>> [메인 스레드] 작업  진행중... 2
[비동기 작업] 작업A 진행중... 3
[비동기 작업] 작업A 진행중... 4
>> [메인 스레드] 작업  진행중... 3
[비동기 작업] 작업A 진행중... 5
------ 모든 비동기 작업 완료 ------
complete futureA result = 김철수
complete futureB result = 이영희
>> [메인 스레드] 작업  진행중... 4
>> [메인 스레드] 작업  진행중... 5
메인 스레드 작업 완료
===== [Async][Non-Blocking] - 종료 =====
```