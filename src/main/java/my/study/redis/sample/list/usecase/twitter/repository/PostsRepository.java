package my.study.redis.sample.list.usecase.twitter.repository;

import my.study.redis.sample.list.usecase.twitter.entity.Posts;
import my.study.redis.sample.list.usecase.twitter.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    List<Posts> findTop100ByUserOrderByRegYmdtDescIdDesc(Users user);

    @Query(value = "SELECT COUNT(p) FROM Posts p")
    Integer findAllCount();

    @Query(value="SELECT p FROM Posts p WHERE p.id IN (:postsIds) ORDER BY p.regYmdt DESC, p.id DESC")
    List<Posts> findTop10ByLatestList(@Param("postsIds") List<Long> postsIds);
}
