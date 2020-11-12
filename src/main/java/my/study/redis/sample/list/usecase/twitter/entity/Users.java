package my.study.redis.sample.list.usecase.twitter.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "UNQ_userId", columnNames = {"user_id"})
})
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue
    @Column(name = "uid")
    private long id;

    @Column(name = "user_id")
    private String userId;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Posts> postsList = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    public Users(String userId) {
        this.userId = userId;
    }
}
