package my.study.redis.sample.hash.simple;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import my.study.redis.RedisApplication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/redis/hash")
@RequiredArgsConstructor
public class HashRedisSimple {
    private final HashRedisSampleService hashRedisSampleService;

    @PostMapping(value = "/saves", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saves(@RequestBody Req param) {
        hashRedisSampleService.hSave(param.keyOfHash, param.values);
        return ResponseEntity.ok("Saved hash data!!");
    }

    @GetMapping("/get")
    public ResponseEntity<Object> get(String keyOfHash, String keyOfHashKey) {
        return ResponseEntity.ok(hashRedisSampleService.hGet(keyOfHash, keyOfHashKey).orElseThrow(RedisApplication.NotFoundException::new));
    }

    @GetMapping("/getAll")
    public ResponseEntity<Object> getAll(String keyOfHash) {
        return ResponseEntity.ok(hashRedisSampleService.hGetAll(keyOfHash).orElseThrow(RedisApplication.NotFoundException::new));
    }

    @Getter
    @Setter
    @NoArgsConstructor
    private static class Req {
        String keyOfHash;
        Map<String, String> values;
    }
}
