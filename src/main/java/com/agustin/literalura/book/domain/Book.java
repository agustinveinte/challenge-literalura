package com.agustin.literalura.book.domain;

import com.agustin.literalura.author.domain.Author;
import com.agustin.literalura.book.api.BookApiDTO;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "books", uniqueConstraints = @UniqueConstraint(columnNames = "title"))
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String title;
    private Integer downloadCount;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();

    @ElementCollection(targetClass = Language.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "book_languages", joinColumns = @JoinColumn(name = "book_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Set<Language> languages = new HashSet<>();

    public Book() {
    }

    public Book(String title, Integer downloadCount) {
        this.title = title;
        this.downloadCount = downloadCount;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<Language> languages) {
        this.languages = languages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return title != null && title.equals(book.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", downloadCount=" + downloadCount +
                ", authors=" + authors +
                ", languages=" + languages +
                '}';
    }
}
