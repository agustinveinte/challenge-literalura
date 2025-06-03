package com.agustin.literalura.book.api;

import com.agustin.literalura.author.api.AuthorApiDTO;
import com.agustin.literalura.book.model.Language;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookApiDTO(
        Integer id,
        String title,
        List<AuthorApiDTO> authors,
        List<String> languages,
        Integer download_count
) {
}
