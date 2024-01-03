package com.springmvcapp.controller;

import com.springmvcapp.model.BookModel;
import com.springmvcapp.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    public static final String USER_LOGIN = "userLogin";
    private final BookService bookService;

    @GetMapping
    public String getBookPage(@RequestParam(required = false, name = "login") String login,
                              @RequestParam(required = false) String email,
                              Model model,
                              HttpServletRequest request) {

        HttpSession session = request.getSession();
        if (login != null && !login.isEmpty()) {
            session.setAttribute(USER_LOGIN, login);
        }

        String userLogin = (String) session.getAttribute(USER_LOGIN);

        model.addAttribute(USER_LOGIN, userLogin);

        List<BookModel> books = bookService.getAllBooksByLogin(userLogin);

        model.addAttribute("userBooks", books);
        return "book_page";
    }

    @GetMapping("/create")
    public String getCreateBookPage(Model model) {
        model.addAttribute("newBook", new BookModel());
        return "create_book_page";
    }

    @PostMapping("/createBook")
    public String createBook(@ModelAttribute BookModel book) {
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{title}")
    public String getEditBookPage(Model model, @PathVariable String title) {
        BookModel byTitle = bookService.findByTitleAndDelete(title);
        model.addAttribute("bookToEdit", byTitle);
        return "edit_book_page";
    }

    @PostMapping("/editBook")
    public String editBook(@ModelAttribute BookModel book) {
        bookService.edit(book);
        return "redirect:/books";
    }

    @GetMapping("/delete/{title}")
    public String delete(@PathVariable String title) {
        bookService.delete(title);
        return "redirect:/books";
    }
}
