package com.agustin.literalura.view;

import com.agustin.literalura.author.api.AuthorApiDTO;
import com.agustin.literalura.author.service.AuthorService;
import com.agustin.literalura.author.view.AuthorViewDTO;
import com.agustin.literalura.book.api.BookApi;
import com.agustin.literalura.book.api.BookApiDTO;
import com.agustin.literalura.book.domain.Language;
import com.agustin.literalura.book.service.BookService;
import com.agustin.literalura.book.service.TheBookAlreadyExistsException;
import com.agustin.literalura.book.view.BookViewDTO;

import java.util.*;
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
        int op = -1;
        while (op!=0) {
            System.out.println("""
                    MENU PRINCIPAL
                    1-Buscar libros por titulo.
                    2-Listar libros registrados.
                    3-Listar autores registrados.
                    4-Listar autores vivios en un determinado año.
                    5-Listar libros por idioma.
                    0-Salir.
                    """);
            System.out.println("Ingrese el numero de opcion:");
            if (consoleInput.hasNextInt()) {
                op = consoleInput.nextInt();
                switch (op) {
                    case 1:
                        saveBooksOnDatabase(bookApi);
                        break;
                    case 2:
                        listBooks();
                        break;
                    case 3:
                        listAuthors();
                        break;
                    case 4:
                        listAuthorsAliveInYear();
                        break;
                    case 5:
                        listBooksByLanguage();
                        break;
                    case 0:
                        System.out.println("App finalizada");
                        break;
                    default:
                        System.out.println("Opcion no valida");
                        break;
                }
                } else{
                    String entradaInvalida = consoleInput.next();
                    System.out.println("Error '" + entradaInvalida + "'. Por favor, ingresa un número entero.");
                }
            }

        }


    public List<BookApiDTO> getBooksFromApi(BookApi bookApi) {
        String busqueda;
        List<BookApiDTO> books = new ArrayList<>();
        System.out.println("Ingrese el titulo del libro que desea buscar:");
        busqueda = consoleInput.next();
        consoleInput.nextLine();
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
                op = consoleInput.next();
                consoleInput.nextLine();
                if (op.equals("all")) {
                    System.out.println("Todos seleccionados");
                    for (BookApiDTO bookApiDTO : booksFromApi) {
                        try {
                            bookService.saveBook(bookApiDTO);
                        } catch (TheBookAlreadyExistsException e) {
                            System.out.println("El libro " + bookApiDTO.title() + " ya se encuentra en la base de datos");
                        } catch (Exception e){
                            System.out.println("El libro " + bookApiDTO.title() + " no pudo guardarse");
                        }
                    }
                    break;
                } else {
                    try {
                        if (op.isEmpty() || Integer.parseInt(op.trim()) == 0) {
                            System.out.println("Ninguno seleccionado");
                            break;
                        } else {
                            BookApiDTO bookSelected = booksFromApi.get(Integer.parseInt(op.trim()) - 1);
                            try {
                                bookService.saveBook(bookSelected);
                                break;
                            } catch (TheBookAlreadyExistsException e) {
                                System.out.println("El libro " + bookSelected.title() + " ya se encuentra en la base de datos");
                            }catch (Exception e){
                                System.out.println("El libro " + bookSelected.title() + " no pudo guardarse");
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

    public void listBooks() {
        Set<BookViewDTO> books = new HashSet<>();
        books = bookService.listBooks();
        if (!books.isEmpty()) {
            for (BookViewDTO book : books) {
                System.out.println("-----LIBRO-----");
                System.out.println("Titulo: " + book.title());
                System.out.println("Autores: " + book.authors());
                System.out.println("Idiomas:" + book.languages());
                System.out.println("Numero de descargas: " + book.downloadCount());
                System.out.println("---------------" + "\n");
            }

        } else {
            System.out.println("No hay libros registrados");
        }
        consoleInput.nextLine();
        System.out.println("Presiona Enter para volver al menú...");
        consoleInput.nextLine();
    }

    public void listAuthors() {
        Set<AuthorViewDTO> authors = new HashSet<>();
        authors = authorService.listAuthors();
        if (!authors.isEmpty()) {
            for (AuthorViewDTO author : authors) {
                System.out.println("Autor: " + author.name());
                System.out.println("Fecha de nacimiento: " + author.birthYear());
                System.out.println("Fecha de fallecimiento:" + author.deathYear());
                System.out.println("Libros: " + author.books() + "\n");
            }

        } else {
            System.out.println("No hay autores registrados");
        }
        consoleInput.nextLine();
        System.out.println("Presiona Enter para volver al menú...");
        consoleInput.nextLine();
    }

    public void listAuthorsAliveInYear() {
        String year;
        Set<AuthorViewDTO> authors = new HashSet<>();
        System.out.println("Ingrese el año que desea consultar: ");
        try {
            year = consoleInput.next();
            authors = authorService.listAuthorsAliveInYear(Integer.parseInt(year));
            if (!authors.isEmpty()) {
                for (AuthorViewDTO author : authors) {
                    System.out.println("Autor: " + author.name());
                    System.out.println("Fecha de nacimiento: " + author.birthYear());
                    System.out.println("Fecha de fallecimiento:" + author.deathYear());
                    System.out.println("Libros: " + author.books() + "\n");
                }

            } else {
                System.out.println("No se encontraron autores que vivieron en esa fecha");
            }
            consoleInput.nextLine();
            System.out.println("Presiona Enter para volver al menú...");
            consoleInput.nextLine();
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese un año valido");
        }
    }

    public void listBooksByLanguage() {
        List<Language> languages = Arrays.stream(Language.values()).toList();
        while (true) {
            System.out.println("Lenguajes:");
            int i = 1;
            for (Language language : languages) {
                System.out.println(i + "-" + language.getDisplayName());
                i++;
            }
            System.out.println("0-" + "Salir");
            String op;
            System.out.println("Seleccione el numero del idioma que desea buscar");
            op = consoleInput.next();
            consoleInput.nextLine();
            try {
                int number = Integer.parseInt(op);
                if (number == 0) {
                    break;
                }
                Set<BookViewDTO> books = bookService.listBooksByLanguage(languages.get(number - 1));
                if (books.isEmpty()) {
                    System.out.println("No se encontraron libros con este idioma");

                } else {
                    for (BookViewDTO book : books) {
                        System.out.println("-----LIBRO-----");
                        System.out.println("Titulo: " + book.title());
                        System.out.println("Autores: " + book.authors());
                        System.out.println("Idiomas:" + book.languages());
                        System.out.println("Numero de descargas: " + book.downloadCount());
                        System.out.println("---------------" + "\n");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese un numero");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Opcion no valida");
            }
            System.out.println("Presiona Enter para volver a consultar otro idioma...");
            consoleInput.nextLine();
        }
    }

}
