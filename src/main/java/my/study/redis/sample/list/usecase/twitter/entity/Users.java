package my.study.redis.sample.list.usecase.twitter.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue
    @Column(name = "uid")
    private long id;

    @Column(name = "user_id")
    private String userId;

    @OneToMany(mappedBy = "user")
    private List<Posts> postsList = new ArrayList<>();

    @
}
