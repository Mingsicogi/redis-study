package my.study.redis.sample.list.usecase.twitter.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "posts")
public class Posts {

    @Id
    @GeneratedValue
    @Column(name = "posts_id")
    private long id;

    @Column(name = "contents")
    private String contents;

    @Column(name = "reg_ymdt")
    private LocalDateTime regYmdt;

    @ManyToOne
    @JoinColumn(name = "uid")
    private Users user;

    @OneToMany
    private List<PostsLike> likes = new ArrayList<>();

    @OneToMany
    private List<Comment> comments = new ArrayList<>();
}
