package my.study.redis.sample.list.usecase.twitter.repository;

import my.study.redis.sample.list.usecase.twitter.entity.Comment;
import my.study.redis.sample.list.usecase.twitter.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {


}
