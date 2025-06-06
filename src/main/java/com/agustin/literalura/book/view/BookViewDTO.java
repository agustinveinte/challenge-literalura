package com.agustin.literalura.book.view;

import java.util.Set;

public record BookViewDTO(
        String title,
        Set<String> authors,
        Integer downloadCount,
        Set<String> languages
) {
}
