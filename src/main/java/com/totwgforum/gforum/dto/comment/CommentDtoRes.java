package com.totwgforum.gforum.dto.comment;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentDtoRes {

    private Long id;

    private String description;
    private String created;
    private String author;


}
