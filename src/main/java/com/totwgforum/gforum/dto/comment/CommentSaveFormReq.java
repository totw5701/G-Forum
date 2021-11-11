package com.totwgforum.gforum.dto.comment;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentSaveFormReq {

    @NotNull
    private String description;

    private LocalDateTime created;

    @NotNull
    private Long author;

    @NotNull
    private Long postId;
}
