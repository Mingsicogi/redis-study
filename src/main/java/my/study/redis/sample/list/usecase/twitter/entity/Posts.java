package my.study.redis.sample.list.usecase.twitter.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "posts", indexes = {
        @Index(columnList = "reg_ymdt")
})
public class Posts {

    @Id
    @GeneratedValue
    @Column(name = "posts_id")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "contents")
    private String contents;

    @Column(name = "reg_ymdt")
    private LocalDateTime regYmdt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uid")
    private Users user;

    @OneToMany(mappedBy = "posts", fetch = FetchType.LAZY)
    private List<PostsLike> likes = new ArrayList<>();

    @OneToMany(mappedBy = "posts", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();
}
