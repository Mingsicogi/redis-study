package my.study.redis.sample.list.usecase.twitter.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "posts", indexes = {
        @Index(columnList = "reg_ymdt")
})
@NoArgsConstructor
public class Posts implements Serializable {

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

    public Posts(String title, String contents, LocalDateTime regYmdt, Users user) {
        this.title = title;
        this.contents = contents;
        this.regYmdt = regYmdt;
        this.user = user;
    }
}
