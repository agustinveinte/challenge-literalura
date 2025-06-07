package com.agustin.literalura;

import com.agustin.literalura.author.service.AuthorService;
import com.agustin.literalura.book.api.BookApi;
import com.agustin.literalura.book.api.GutendexApi;
import com.agustin.literalura.book.service.BookService;
import com.agustin.literalura.view.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

    @Autowired
    BookService bookService;
    @Autowired
    AuthorService authorService;

    public static void main(String[] args) {
        SpringApplication.run(LiteraluraApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Menu menu = new Menu(bookService,authorService);
        BookApi bookApi=new GutendexApi();
        menu.menuPrincipal(bookApi);
    }
}
