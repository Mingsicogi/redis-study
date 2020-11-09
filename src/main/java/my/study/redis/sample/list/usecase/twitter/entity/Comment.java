package my.study.redis.sample.list.usecase.twitter.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private long id;

    @Column(name = "contents")
    private String content;

    @Column(name = "reg_ymdt")
    private LocalDateTime regYmdt;

    @ManyToOne
    @JoinColumn(name = "uid")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "posts_id")
    private Posts posts;
}
