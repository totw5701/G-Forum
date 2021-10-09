package com.totwgforum.gforum.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostDtoRes {

    private Long id;
    private String title;
    private String date;
    private Long authorId;
    private String authorNickname;
    private String description;
}
