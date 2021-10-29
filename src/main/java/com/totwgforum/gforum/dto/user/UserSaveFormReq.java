package com.totwgforum.gforum.dto.user;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserSaveFormReq {

    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String nickName;
    @NotBlank
    private String password;
    @NotBlank
    private String passwordConfirm;
}
