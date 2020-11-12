package my.study.redis.sample.list.usecase.twitter.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "posts_like")
public class PostsLike {

    @Id
    @GeneratedValue
    @Column(name = "posts_like_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "likes")
    private Posts posts;

    @Column(name = "reg_ymdt")
    private LocalDateTime regYmdt;
}
