package com.springmvcapp.model;

import lombok.Data;

@Data
public class UserModel {

    private String username;
    private String password;
    private UserRole role;
}
