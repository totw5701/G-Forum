package com.totwgforum.gforum.dto.user;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserLoginFormReq {

    @NotNull
    private String email;

    @NotNull
    private String password;
}
