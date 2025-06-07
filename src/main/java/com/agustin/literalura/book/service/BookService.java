package com.agustin.literalura.book.service;

import com.agustin.literalura.author.domain.Author;
import com.agustin.literalura.author.domain.AuthorRepository;
import com.agustin.literalura.book.api.BookApiDTO;
import com.agustin.literalura.book.domain.Book;
import com.agustin.literalura.book.domain.BookRepository;
import com.agustin.literalura.book.domain.Language;
import com.agustin.literalura.book.view.BookViewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;

    @Transactional
    public void saveBook(BookApiDTO book) throws TheBookAlreadyExistsException {
        try {
            //Comprobar si el libro ya existe
            if (bookRepository.findByTitle(book.title()).isPresent()) {
                throw new TheBookAlreadyExistsException();
            } else {
                Book newBook = new Book(book.title(), book.downloadCount());
                Set<Language> languages = book.languages().stream().map(Language::fromString).collect(Collectors.toSet());
                Set<Author> authors = new HashSet<>();
                //comprobar si los autores estan registrados, si no guardarlos
                book.authors().forEach(authorApiDTO ->
                {
                    Optional<Author> newAuthor = authorRepository.findByName(authorApiDTO.name());
                    if (newAuthor.isPresent()) {
                        authors.add(newAuthor.get());
                    } else {
                        authors.add(authorRepository.save(new Author(authorApiDTO.name(), authorApiDTO.birthYear(), authorApiDTO.deathYear())));
                    }
                });
                //registrar libro
                newBook.setLanguages(languages);
                newBook.setAuthors(authors);
                bookRepository.save(newBook);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Transactional
    public Set<BookViewDTO> listBooks() {
        Set<BookViewDTO> books = new HashSet<>();
        return bookRepository.findAll().stream().map(book ->
                {
                    Set<String> authors = book.getAuthors().stream().map(Author::getName).collect(Collectors.toSet());
                    Set<String> languages = book.getLanguages().stream().map(Language::getDisplayName).collect(Collectors.toSet());
                    return new BookViewDTO(book.getTitle(),authors,book.getDownloadCount(),languages);
                }
        ).collect(Collectors.toSet());
    }

    @Transactional
    public Set<BookViewDTO> listBooksByLanguage(Language language) {
        Set<BookViewDTO> books = new HashSet<>();
        return bookRepository.findBooksByLanguage(language).stream().map(book ->
                {
                    Set<String> authors = book.getAuthors().stream().map(Author::getName).collect(Collectors.toSet());
                    Set<String> languages = book.getLanguages().stream().map(Language::getDisplayName).collect(Collectors.toSet());
                    return new BookViewDTO(book.getTitle(),authors,book.getDownloadCount(),languages);
                }
        ).collect(Collectors.toSet());
    }

}

