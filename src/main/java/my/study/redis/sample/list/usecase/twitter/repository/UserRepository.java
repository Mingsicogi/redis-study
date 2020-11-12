package my.study.redis.sample.list.usecase.twitter.repository;

import my.study.redis.sample.list.usecase.twitter.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByUserId(String userId);

}
