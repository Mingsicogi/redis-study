package my.study.redis.sample.set.simple;

import io.lettuce.core.api.sync.RedisCommands;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class SetRedisSampleService {

    private final RedisCommands<String, String> redisCommands;

    public void sAdd(String keyOfSet, String... values) {
        Long result = redisCommands.sadd(keyOfSet, values);
        log.info("### set key : {}, result : {}", keyOfSet, result);
    }

    public Optional<Set<String>> sGet(String keyOfSet) {
        return Optional.ofNullable(redisCommands.smembers(keyOfSet));
    }

    public Boolean isExist(String keyOfSet, String keyOfSetKey) {
        return redisCommands.sismember(keyOfSet, keyOfSetKey);
    }

    public Optional<Set<String>> union(String... keyOfSet) {
        return Optional.ofNullable(redisCommands.sunion(keyOfSet));
    }

    public Optional<Set<String>> intersection(String... keyOfSet) {
        return Optional.ofNullable(redisCommands.sinter(keyOfSet));
    }
}
