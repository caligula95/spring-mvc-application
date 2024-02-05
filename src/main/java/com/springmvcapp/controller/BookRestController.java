package com.springmvcapp.controller;

import com.springmvcapp.model.BookModel;
import com.springmvcapp.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;

    @GetMapping
    public List<BookModel> getAllBooks(@AuthenticationPrincipal UserDetails userDetails) {
        return bookService.getAllBooksByLogin(userDetails.getUsername());
    }
}
