package com.totwgforum.gforum.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostDtoReq {
    private Long id;
    private Long author;
    private String title;
    private String description;
}
