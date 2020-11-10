package my.study.redis.sample.set.simple;

import lombok.RequiredArgsConstructor;
import my.study.redis.RedisApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis/set")
@RequiredArgsConstructor
public class SetRedisSample {

    private final SetRedisSampleService setRedisSampleService;

    @GetMapping("/add")
    public ResponseEntity<Object> add(String keyOfSet, String... values) {
        setRedisSampleService.sAdd(keyOfSet, values);
        return ResponseEntity.ok("Saved set data!!");
    }

    @GetMapping("/get")
    public ResponseEntity<Object> get(String keyOfSet) {
        return ResponseEntity.ok(setRedisSampleService.sGet(keyOfSet).orElseThrow(RedisApplication.NotFoundException::new));
    }

    @GetMapping("/exist")
    public ResponseEntity<Object> exist(String keyOfSet, String keyOfSetKey) {
        return ResponseEntity.ok(setRedisSampleService.isExist(keyOfSet, keyOfSetKey));
    }

    @GetMapping("/union")
    public ResponseEntity<Object> union(String... keyOfSet) {
        return ResponseEntity.ok(setRedisSampleService.union(keyOfSet).orElseThrow(RedisApplication.NotFoundException::new));
    }

    @GetMapping("/intersection")
    public ResponseEntity<Object> intersection(String... keyOfSet) {
        return ResponseEntity.ok(setRedisSampleService.intersection(keyOfSet).orElseThrow(RedisApplication.NotFoundException::new));
    }
}
