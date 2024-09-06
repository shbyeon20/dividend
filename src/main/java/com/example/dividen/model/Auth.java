package com.example.dividen.model;

import lombok.Data;

import java.util.List;

public class Auth {

    @Data
    public static class SignInRequest {
        private String userName;
        private String password;

    }

    @Data
    public static class SignUpRequest {
        private String userName;
        private String password;
        private List<String> roles;
    }
}
