package my.study.redis.sample.string.simple;

import lombok.RequiredArgsConstructor;
import my.study.redis.RedisApplication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/redis/string")
@RequiredArgsConstructor
public class StringRedisSample {

    private final StringRedisSampleService stringRedisSampleService;

    @GetMapping("/save")
    public ResponseEntity<Object> save(String key, String value, Integer seconds) {

        if (seconds == null || seconds == 0) {
            stringRedisSampleService.save(key, value);
        } else {
            stringRedisSampleService.saveWithExpireTime(key, value, seconds);
        }

        return ResponseEntity.ok("Saved!!");
    }

    @GetMapping("/get")
    public ResponseEntity<Object> get(String key) {
        return ResponseEntity.ok(stringRedisSampleService.get(key).orElseThrow(RedisApplication.NotFoundException::new));
    }


    @GetMapping("/isExist")
    public ResponseEntity<Object> isExist(String key) {
        return ResponseEntity.ok(stringRedisSampleService.isExist(key));
    }

    @GetMapping("/type")
    public ResponseEntity<Object> type(String key) {
        return ResponseEntity.ok(stringRedisSampleService.getDataType(key).orElseThrow(RedisApplication.NotFoundException::new));
    }

    @GetMapping("/delKey")
    public ResponseEntity<Object> delKey(String key) {
        return stringRedisSampleService.delKey(key) ? ResponseEntity.ok("OK") : ResponseEntity.badRequest().body("FAIL");
    }

    @PostMapping(value = "/saves", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saves(@RequestBody Map<String, String> keysAndValues) {
        stringRedisSampleService.saves(keysAndValues);
        return ResponseEntity.ok("Saves all!!");
    }

    @PostMapping(value = "/getList", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getList(@RequestBody List<String> keys) {
        return ResponseEntity.ok(stringRedisSampleService.getList(keys));
    }
}
