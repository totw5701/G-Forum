package com.totwgforum.gforum.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@ToString
public class User {

    @Id @GeneratedValue
    private Long id;

    private String email;
    private String nickName;
    private String password;
    private LocalDateTime registerDate;

    @Enumerated(EnumType.STRING)
    private UserRole role;
}
