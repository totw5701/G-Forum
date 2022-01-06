package com.totwgforum.gforum.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Comment {

    @Id @GeneratedValue
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String description;
    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User author;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;

}
