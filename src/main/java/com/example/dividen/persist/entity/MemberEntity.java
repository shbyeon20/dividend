package com.example.dividen.persist.entity;

import com.example.dividen.model.Auth;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@Entity(name = "MEMBER")
@NoArgsConstructor
@Builder
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;


    @ElementCollection
    private List<String> roles;

public static MemberEntity fromSignUp(Auth.SignUpRequest signUpRequest) {
    return MemberEntity.builder()
            .username(signUpRequest.getUserName())
            .password(signUpRequest.getPassword())
            .roles(signUpRequest.getRoles())
            .build();
}


}


