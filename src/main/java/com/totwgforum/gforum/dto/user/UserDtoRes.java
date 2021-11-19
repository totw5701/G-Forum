package com.totwgforum.gforum.dto.user;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoRes {

    private Long id;
    private String email;
    private String nickName;
    private String password;
    private LocalDateTime registerDate;

}
