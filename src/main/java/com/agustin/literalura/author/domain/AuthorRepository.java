package com.agustin.literalura.author.domain;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author,Long> {
    Optional<Author>findByName(String name);
    @Override
    @EntityGraph(attributePaths = {"books", "books.languages"})
    List<Author> findAll();

}
