package com.agustin.literalura.book.api;

import com.agustin.literalura.author.api.AuthorApiDTO;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookApiDTO(
        @JsonAlias("id")
        Integer id,
        @JsonAlias("title")
        String title,
        @JsonAlias("authors")
        Set<AuthorApiDTO> authors,
        @JsonAlias("languages")
        Set<String> languages,
        @JsonAlias("download_count")
        Integer downloadCount
) {
}
