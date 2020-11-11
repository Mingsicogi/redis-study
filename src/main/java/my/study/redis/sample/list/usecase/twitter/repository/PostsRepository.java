package my.study.redis.sample.list.usecase.twitter.repository;

import my.study.redis.sample.list.usecase.twitter.entity.Posts;
import my.study.redis.sample.list.usecase.twitter.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {


}
