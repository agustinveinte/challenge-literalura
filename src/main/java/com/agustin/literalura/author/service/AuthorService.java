package com.agustin.literalura.author.service;

import com.agustin.literalura.author.domain.AuthorRepository;
import com.agustin.literalura.author.view.AuthorViewDTO;
import com.agustin.literalura.book.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    public Set<AuthorViewDTO> listAuthors(){
        Set<AuthorViewDTO> authors = new HashSet<>();
        return authorRepository.findAll().stream().map(author ->
                {
                    Set<String> books = author.getBooks().stream().map(Book::getTitle).collect(Collectors.toSet());
                    return new AuthorViewDTO(author.getName(),author.getBirthYear(),author.getDeathYear(),books);
                }
        ).collect(Collectors.toSet());
    }

    public Set<AuthorViewDTO> listAuthorsAliveInYear(Integer year){
        Set<AuthorViewDTO> authors = new HashSet<>();
        return authorRepository.findAuthorsAliveInYear(year).stream().map(author ->
                {
                    Set<String> books = author.getBooks().stream().map(Book::getTitle).collect(Collectors.toSet());
                    return new AuthorViewDTO(author.getName(),author.getBirthYear(),author.getDeathYear(),books);
                }
        ).collect(Collectors.toSet());
    }
}
