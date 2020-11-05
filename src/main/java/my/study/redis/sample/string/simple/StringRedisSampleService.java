package my.study.redis.sample.string.simple;

import io.lettuce.core.KeyValue;
import io.lettuce.core.Value;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.study.redis.RedisApplication;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StringRedisSampleService {

    private final StringRedisTemplate stringRedisTemplate;

    private final RedisCommands<String, String> redisCommands;

    /**
     * set redis command
     *
     * @param key
     * @param value
     */
    public void save(String key, String value) {
//        Integer result = stringRedisTemplate.opsForValue().append(key, value);
        String successMsg = redisCommands.set(key, value);
        log.info("# key : {}, value : {}, result : {}", key, value, successMsg);
    }

    public Boolean saveWithExpireTime(String key, String value, Integer seconds) {
        String successMsg = redisCommands.set(key, value);
        log.info("# key : {}, value : {}, result : {}", key, value, successMsg);
        return redisCommands.expire(key, seconds);
    }

    /**
     * get redis command
     *
     * @param key
     * @return
     */
    public Optional<String> get(String key) {
//        return Optional.ofNullable(stringRedisTemplate.opsForValue().get(key));
        return Optional.ofNullable(redisCommands.get(key));
    }

    /**
     * exist redis command
     *
     * @param key
     * @return
     */
    public Boolean isExist(String key) {
        return redisCommands.exists(key) == 1 ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * type redis command
     *
     * @param key
     * @return
     */
    public Optional<String> getDataType(String key) {
        return Optional.ofNullable(redisCommands.type(key));
    }

    /**
     * delete redis command
     *
     * @param key
     * @return
     */
    public boolean delKey(String key) {
        return redisCommands.del(key) == 1;
    }

    /**
     * mset redis command
     *
     * @param keysAndValues
     */
    public void saves(Map<String, String> keysAndValues) {
        String resultMsg = redisCommands.mset(keysAndValues);
        log.info("result : {}", resultMsg);
    }

    /**
     * mget redis command
     *
     * @param keys
     * @return
     */
    public List<String> getList(List<String> keys) {
        List<KeyValue<String, String>> getList = redisCommands.mget(keys.toArray(String[]::new));
        if(getList.isEmpty()) {
            throw new RedisApplication.NotFoundException();
        }

        return getList.stream().map(Value::getValue).collect(Collectors.toList());
    }
}
