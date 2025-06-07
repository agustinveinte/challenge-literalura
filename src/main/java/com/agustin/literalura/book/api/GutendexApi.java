package com.agustin.literalura.book.api;

import com.agustin.literalura.util.api.ApiConsumer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class GutendexApi implements BookApi{
    private final String URL="https://gutendex.com/books/?search=";
    private final ApiConsumer apiConsumer = new ApiConsumer();

    @Override
    public List<BookApiDTO> getBooksByTitleOrAuthor(String titleOrAuthor) {
        List<BookApiDTO> books=new ArrayList<>();
        try {
            String json = apiConsumer.obtenerDatos(URL+titleOrAuthor.replace(" ","%20"));
            ObjectMapper mapper = new ObjectMapper();
            // Parsear el JSON como árbol
            JsonNode rootNode = null;
            rootNode = mapper.readTree(json);
            // Extraer el nodo "results"
            JsonNode resultsNode = rootNode.get("results");
            books = mapper.readValue(resultsNode.toString(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return books;
    }
}
