package com.totwgforum.gforum.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
@ToString
public class User {

    @Id @GeneratedValue
    private Long id;

    private String email;
    private String nickName;
    private String password;
    private LocalDateTime registerDate;

    @OneToMany(mappedBy = "user")
    List<Post> posts = new ArrayList<>();

}
