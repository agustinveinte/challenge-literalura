package com.agustin.literalura.book.domain;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);

    @EntityGraph(attributePaths = {
            "authors",
            "languages",})
    List<Book> findAll();
}
