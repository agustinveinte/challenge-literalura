package com.agustin.literalura.book.api;

import java.util.List;

public interface BookApi {
    List<BookApiDTO> getBooksByTitleOrAuthor(String titleOrAuthor);
}
