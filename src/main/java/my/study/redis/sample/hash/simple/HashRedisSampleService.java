package my.study.redis.sample.hash.simple;

import io.lettuce.core.api.sync.RedisCommands;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HashRedisSampleService {

    private final RedisCommands<String, String> redisCommands;

    public void hSave(String keyOfHash, Map<String, String> values) {
        String result = redisCommands.hmset(keyOfHash, values);
        log.info("result : {}", result);
    }

    public Optional<String> hGet(String keyOfHash, String keyOfHashKey) {
        return Optional.ofNullable(redisCommands.hget(keyOfHash, keyOfHashKey));
    }

    public Optional<Map<String, String>> hGetAll(String keyOfHash) {
        return Optional.ofNullable(redisCommands.hgetall(keyOfHash));
    }
}
