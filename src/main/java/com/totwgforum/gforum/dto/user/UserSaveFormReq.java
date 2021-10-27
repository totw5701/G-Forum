package com.totwgforum.gforum.dto.user;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserSaveFormReq {

    @Email
    @NotNull
    private String email;
    @NotNull
    private String nickName;
    @NotNull
    private String password;
    @NotNull
    private String passwordConfirm;
}
