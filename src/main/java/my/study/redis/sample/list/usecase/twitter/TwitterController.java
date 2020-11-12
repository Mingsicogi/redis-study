package my.study.redis.sample.list.usecase.twitter;

import lombok.RequiredArgsConstructor;
import my.study.redis.sample.list.usecase.twitter.entity.Posts;
import my.study.redis.sample.list.usecase.twitter.entity.Users;
import my.study.redis.sample.list.usecase.twitter.repository.PostsRepository;
import my.study.redis.sample.list.usecase.twitter.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/twitter/example")
@RequiredArgsConstructor
public class TwitterController {

    private final PostsRepository postsRepository;
    private final UserRepository userRepository;
    final StopWatch stopWatch = new StopWatch();

    @GetMapping("/posts/latest10")
    public String list(@RequestParam String userId, Model model) {
        stopWatch.start();
        Users user = userRepository.findByUserId(userId);
        List<Posts> latestPostsList = postsRepository.findTop10ByUserOrderByRegYmdtDesc(user);
        stopWatch.stop();

        model.addAttribute("latestPostsList", latestPostsList);
        model.addAttribute("user", user);
        model.addAttribute("pageLoadingTime", stopWatch.getTotalTimeSeconds());

        return "twitter/list";
    }

    @GetMapping("/user/add")
    @ResponseBody
    public ResponseEntity<Object> addUser(@RequestParam String userId){
        Users save = userRepository.save(new Users(userId));
        return ResponseEntity.ok(save);
    }

//    @GetMapping("/posts/latest/usingRedis")
//    public String list(@RequestParam Integer count, Model model) {
//
//    }
}
