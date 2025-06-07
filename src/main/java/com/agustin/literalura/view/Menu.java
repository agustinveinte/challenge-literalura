package com.agustin.literalura.view;

import com.agustin.literalura.author.api.AuthorApiDTO;
import com.agustin.literalura.author.domain.AuthorRepository;
import com.agustin.literalura.author.service.AuthorService;
import com.agustin.literalura.book.api.BookApi;
import com.agustin.literalura.book.api.BookApiDTO;
import com.agustin.literalura.book.service.BookService;
import com.agustin.literalura.book.service.TheBookAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Menu {
    private BookService bookService;
    private AuthorService authorService;

    private final Scanner consoleInput = new Scanner(System.in);
    private BookApi bookApi;

    public Menu(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    public void menuPrincipal(BookApi bookApi) {
        System.out.println("""
                1-Buscar libros por titulo.
                2-Listar libros registrados.
                3-Listar autores registrados.
                4-Listar autores vivios en un determinado año.
                5-Listar libros por idioma.
                0-Salir.
                """);
        int op;
        do {
            System.out.println("Ingrese el numero de opcion:");
            op = consoleInput.nextInt();
            switch (op) {
                case 1:saveBooksOnDatabase(bookApi);
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
        } while (op != 0);
    }

    public List<BookApiDTO> getBooksFromApi(BookApi bookApi) {
        String busqueda;
        List<BookApiDTO> books = new ArrayList<>();
        System.out.println("Ingrese el titulo del libro que desea buscar");
        busqueda = consoleInput.nextLine();
        try {
            System.out.println("Obteniendo libros...");
            books = bookApi.getBooksByTitleOrAuthor(busqueda);

        } catch (RuntimeException e) {
            System.out.println("Error: Ocurrio un problema al consultar la api.");
        }
        return books;
    }

    public void saveBooksOnDatabase(BookApi bookApi) {
        List<BookApiDTO> booksFromApi = getBooksFromApi(bookApi);
        if (!booksFromApi.isEmpty()) {
            System.out.println(booksFromApi.size() + " libros encontrados:");
            int i = 1;
            for (BookApiDTO bookApiDTO : booksFromApi) {
                String title = bookApiDTO.title();
                Set<String> authors = bookApiDTO.authors().stream().map(AuthorApiDTO::name).collect(Collectors.toSet());
                System.out.println(i + "-" + title + " autores: " + authors);
                i++;
            }
            System.out.println("0-Ninguno");
            while (true) {
                String op;
                System.out.println("Seleccione el numero de libro que desea guardar (si quiere guardar todos ingrese 'all')");
                op = consoleInput.nextLine();
                if (op.equals("all")) {
                    System.out.println("Todos seleccionados");
                    for (BookApiDTO bookApiDTO : booksFromApi) {
                        try {
                            bookService.saveBook(bookApiDTO);
                        } catch (TheBookAlreadyExistsException e) {
                            System.out.println("El libro "+ bookApiDTO.title()+ " ya se encuentra en la base de datos");
                        }
                    }
                    break;
                } else {
                    try {
                        if (op.isEmpty()||Integer.parseInt(op.trim())==0) {
                            System.out.println("Ninguno seleccionado");
                            break;
                        }else {
                            BookApiDTO bookSelected=booksFromApi.get(Integer.parseInt(op.trim())-1);
                            try {
                                bookService.saveBook(bookSelected);
                                break;
                            } catch (TheBookAlreadyExistsException e) {
                                System.out.println("El libro "+ bookSelected.title()+ " ya se encuentra en la base de datos");
                            }

                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Opcion no valida: debe ingresar un numero");
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Opcion no valida");
                    }
                }
            }
        } else {
            System.out.println("No hay resultados");
        }
    }

}
