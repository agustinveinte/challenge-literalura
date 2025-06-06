package com.agustin.literalura;

import com.agustin.literalura.author.api.AuthorApiDTO;
import com.agustin.literalura.author.domain.Author;
import com.agustin.literalura.author.domain.AuthorRepository;
import com.agustin.literalura.book.api.BookApiDTO;
import com.agustin.literalura.book.api.BookApi;
import com.agustin.literalura.book.api.GutendexApi;
import com.agustin.literalura.book.domain.Book;
import com.agustin.literalura.book.domain.BookRepository;
import com.agustin.literalura.book.domain.Language;
import com.agustin.literalura.book.service.BookService;
import com.agustin.literalura.book.service.TheBookAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(LiteraluraApplication.class, args);
    }

    @Override
    public void run(String... args) {
        BookApi api = new GutendexApi();
        List<BookApiDTO> booksApi = api.getBooksByTitleOrAuthor("dickens");
        System.out.println("Obteniendo libros desde la API");
        booksApi.forEach(bookApiDTO -> {
            try {
                bookService.saveBook(bookApiDTO);
            } catch (TheBookAlreadyExistsException e) {
                System.out.println("El libro " + bookApiDTO.title() + " ya existe, no es necesario guardarlo");
            }
        });

        bookService.listAuthors().forEach(System.out::println);


    }
}
