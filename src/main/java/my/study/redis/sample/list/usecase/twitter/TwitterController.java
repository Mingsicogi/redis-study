package my.study.redis.sample.list.usecase.twitter;

import lombok.RequiredArgsConstructor;
import my.study.redis.sample.list.simple.ListRedisSampleService;
import my.study.redis.sample.list.usecase.twitter.entity.Posts;
import my.study.redis.sample.list.usecase.twitter.entity.Users;
import my.study.redis.sample.list.usecase.twitter.repository.PostsRepository;
import my.study.redis.sample.list.usecase.twitter.repository.UserRepository;
import my.study.redis.sample.string.simple.StringRedisSampleService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.SerializationUtils;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/twitter/example")
@RequiredArgsConstructor
public class TwitterController {

    private final ListRedisSampleService listRedisSampleService;
    private final StringRedisSampleService stringRedisSampleService;

    private final PostsRepository postsRepository;
    private final UserRepository userRepository;

    static final String TWITTER_LATEST_POST_COMMON_KEY = "twitter:post:latest:";
    static final String TWITTER_POST_TOTAL_COUNT_KEY = "twitter:post:total:count";
    static final Integer LATEST_COUNT = 100;

    @GetMapping("/posts/latest10")
    public String list(@RequestParam String userId, Model model) {
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        Users user = userRepository.findByUserId(userId);
        List<Posts> latestPostsList = postsRepository.findTop100ByUserOrderByRegYmdtDescIdDesc(user);
        stopWatch.stop();

        model.addAttribute("latestPostsList", latestPostsList);
        model.addAttribute("totalPostsCount", getAllPostCount());
        model.addAttribute("user", user);
        model.addAttribute("pageLoadingTime", stopWatch.getTotalTimeSeconds());
        model.addAttribute("useRedis", false);

        return "twitter/list";
    }

    @GetMapping("/posts/latest10/usingRedis")
    public String listUsingRedis(@RequestParam String userId, Model model) {
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();

        String redisKey = TWITTER_LATEST_POST_COMMON_KEY + userId;
        Optional<List<byte[]>> redisData = listRedisSampleService.getListStartToEnd(redisKey.getBytes(), 0, LATEST_COUNT - 1);
        if(redisData.isPresent()) {
            if(redisData.get().size() == LATEST_COUNT) {
                model.addAttribute("latestPostsList", redisData.get().stream().map(SerializationUtils::deserialize).collect(toList()));
            } else {
                getLatestPostListOnRedis(model, userId, redisKey);
            }
        } else {
            getLatestPostListOnRedis(model, userId, redisKey);
        }
        stopWatch.stop();

        model.addAttribute("user", userRepository.findByUserId(userId));
        model.addAttribute("totalPostsCount", getAllPostCount());
        model.addAttribute("pageLoadingTime", stopWatch.getTotalTimeSeconds());
        model.addAttribute("useRedis", true);

        return "twitter/list";
    }

    private void getLatestPostListOnRedis(Model model, String userId, String redisKey) {
        Users user = userRepository.findByUserId(userId);
        List<Posts> latestPostList = postsRepository.findTop100ByUserOrderByRegYmdtDescIdDesc(user);
        listRedisSampleService.pushToRight(redisKey.getBytes(), latestPostList.stream().map(SerializationUtils::serialize).collect(toList()).toArray(byte[][]::new));
        listRedisSampleService.cutFromTo(redisKey, 0, LATEST_COUNT - 1);

        model.addAttribute("latestPostsList", latestPostList);
    }

    private String getAllPostCount() {
        Optional<String> countAll = stringRedisSampleService.get(TWITTER_POST_TOTAL_COUNT_KEY).or(() -> {
            Integer allCount = postsRepository.findAllCount();
            stringRedisSampleService.save(TWITTER_POST_TOTAL_COUNT_KEY, String.valueOf(allCount));
            return Optional.ofNullable(String.valueOf(allCount));
        });

        return countAll.get();
    }

    @GetMapping("/posts/add")
    public ResponseEntity<Object> addPosts(@RequestParam String title, @RequestParam String contents, @RequestParam String userId) {
        Users user = userRepository.findByUserId(userId);
        Posts posts = new Posts(title, contents, LocalDateTime.now(), user);

        Posts savedPost = postsRepository.save(posts);

        String redisKey = TWITTER_LATEST_POST_COMMON_KEY + userId;
        listRedisSampleService.pushToLeft(redisKey.getBytes(), SerializationUtils.serialize(savedPost));
        stringRedisSampleService.countUp(TWITTER_POST_TOTAL_COUNT_KEY);

        listRedisSampleService.cutFromTo(redisKey, 0, LATEST_COUNT - 1);

        return ResponseEntity.ok("OK");
    }

    @GetMapping("/user/add")
    @ResponseBody
    public ResponseEntity<Object> addUser(@RequestParam String userId){
        Users save = userRepository.save(new Users(userId));
        return ResponseEntity.ok(save);
    }

}
