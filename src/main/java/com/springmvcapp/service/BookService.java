package com.springmvcapp.service;

import com.springmvcapp.model.BookModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private static List<BookModel> books;

    static {
        books = new ArrayList<>();
        books.add(new BookModel("The Great Gatsby", 1925));
        books.add(new BookModel("To Kill a Mockingbird", 1960));
        books.add(new BookModel("1984", 1949));
        books.add(new BookModel("The Catcher in the Rye", 1951));
    }

    public List<BookModel> getAllBooksByLogin(String login) {
        if (login != null) {
            return books;
        }

        return books.stream()
                .filter(book -> book.getYear() > 1951)
                .toList();
    }

    public void save(BookModel book) {
        books.add(book);
    }

    public BookModel findByTitleAndDelete(String title) {
        BookModel bookModel = books.stream()
                .filter(it -> it.getTitle().equals(title))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        books.remove(bookModel);
        return bookModel;
    }

    public void edit(BookModel book) {
        save(book);
    }

    public void delete(String title) {
        findByTitleAndDelete(title);
    }
}
