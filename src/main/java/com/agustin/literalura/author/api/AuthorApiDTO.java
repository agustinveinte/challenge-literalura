package com.agustin.literalura.author.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthorApiDTO(
        String name,
        Integer birth_year,
        Integer death_year
) {
}
