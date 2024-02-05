package com.springmvcapp.controller.request;

import lombok.Data;

@Data
public class UserTokenRequest {

    private String login;
    private String password;
}
