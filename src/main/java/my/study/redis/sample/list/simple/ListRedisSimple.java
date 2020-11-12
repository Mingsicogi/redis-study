package my.study.redis.sample.list.simple;

import lombok.RequiredArgsConstructor;
import my.study.redis.RedisApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis/list")
@RequiredArgsConstructor
public class ListRedisSimple {

    private final ListRedisSampleService listRedisSampleService;

    @GetMapping("/addOnRight")
    public ResponseEntity<Object> addOnRight(String keyOfList, String... values) {
        listRedisSampleService.pushToRight(keyOfList, values);
        return ResponseEntity.ok("Saved on Right list !!");
    }

    @GetMapping("/addOnLeft")
    public ResponseEntity<Object> addOnLeft(String keyOfList, String... values) {
        listRedisSampleService.pushToLeft(keyOfList, values);
        return ResponseEntity.ok("Saved on Left list !!");
    }

    @GetMapping("/getListFromTo")
    public ResponseEntity<Object> getListFromTo(String keyOfList, Integer start, Integer end) {
        return ResponseEntity.ok(listRedisSampleService.getListStartToEnd(keyOfList, start, end).orElseThrow(RedisApplication.NotFoundException::new));
    }

    @GetMapping("/popRight")
    public ResponseEntity<Object> popRight(String keyOfList) {
        return ResponseEntity.ok(listRedisSampleService.popRight(keyOfList).orElseThrow(RedisApplication.NotFoundException::new));
    }

    @GetMapping("/popLeft")
    public ResponseEntity<Object> popLeft(String keyOfList) {
        return ResponseEntity.ok(listRedisSampleService.popLeft(keyOfList).orElseThrow(RedisApplication.NotFoundException::new));
    }

    @GetMapping("/cutFromTo")
    public ResponseEntity<Object> cutFromTo(String keyOfList, Integer start, Integer end) {
        listRedisSampleService.cutFromTo(keyOfList, start, end);
        return ResponseEntity.ok("Cut " + start + " to " + end);
    }

    @GetMapping("/blockingPop")
    public ResponseEntity<Object> blockingPop(String keyOfList, Integer waitingSeconds) {
        return ResponseEntity.ok(listRedisSampleService.blockingPop(keyOfList, waitingSeconds));
    }
}
