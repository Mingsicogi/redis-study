package my.study.redis.sample.list.simple;

import io.lettuce.core.KeyValue;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.study.redis.RedisApplication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListRedisSampleService {

    private final RedisCommands<String, String> redisCommands;

    public Long pushToLeft(String keyOfList, String... value) {
        Long listSize = redisCommands.lpush(keyOfList, value);
        log.info("total {} size : {}", keyOfList, listSize);

        return listSize;
    }

    public Long pushToRight(String keyOfList, String... value) {
        Long listSize = redisCommands.rpush(keyOfList, value);
        log.info("total {} size : {}", keyOfList, listSize);

        return listSize;
    }

    public List<String> getListStartToEnd(String keyOfList, Integer start, Integer end) {
        List<String> values = redisCommands.lrange(keyOfList, start, end);
        if(values == null || values.isEmpty()) {
            throw new RedisApplication.NotFoundException();
        }

        return values;
    }

    public Optional<String> popRight(String keyOfList) {
        return Optional.ofNullable(redisCommands.rpop(keyOfList));
    }

    public Optional<String> popLeft(String keyOfList) {
        return Optional.ofNullable(redisCommands.lpop(keyOfList));
    }

    public void cutFromTo(String keyOfList, Integer start, Integer end) {
        String successMsg = redisCommands.ltrim(keyOfList, start, end);
        log.info("Result : {}", successMsg);
    }

    public String blockingPop(String keyOfList, Integer waitingSeconds){
        KeyValue<String, String> value = Optional.ofNullable(redisCommands.brpop(waitingSeconds, keyOfList)).orElseThrow(RedisApplication.NotFoundException::new);
        return value.getValue();
    }
}
