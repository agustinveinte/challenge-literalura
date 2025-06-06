package com.agustin.literalura.author.api;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthorApiDTO(
        @JsonAlias("name")
        String name,
        @JsonAlias("birth_year")
        Integer birthYear,
        @JsonAlias("death_year")
        Integer deathYear
) {
}
