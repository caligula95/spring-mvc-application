package com.springmvcapp.controller;

import com.springmvcapp.controller.request.UserTokenRequest;
import com.springmvcapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> getToken(@RequestBody UserTokenRequest request) {
        String token = userService.generateToken(request);
        return ResponseEntity.ok(token);
    }
}
