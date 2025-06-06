package com.agustin.literalura.author.view;

import java.util.Set;

public record AuthorViewDTO(
        String name,
        Integer birthYear,
        Integer deathYear,
        Set<String> books
) {
}
