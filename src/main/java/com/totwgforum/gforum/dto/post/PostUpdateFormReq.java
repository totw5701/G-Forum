package com.totwgforum.gforum.dto.post;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostUpdateFormReq {

    @NotNull
    private Long id;
    @NotNull
    private Long author;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
}
