package com.totwgforum.gforum.domain;

import lombok.Getter;
import lombok.Setter;

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
    private Long author;

    private Long postId;

}
