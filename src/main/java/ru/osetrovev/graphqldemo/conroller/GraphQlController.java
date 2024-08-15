package ru.osetrovev.graphqldemo.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import ru.osetrovev.graphqldemo.dto.AuthorDto;
import ru.osetrovev.graphqldemo.dto.BookDto;
import ru.osetrovev.graphqldemo.entity.Author;
import ru.osetrovev.graphqldemo.entity.Book;
import ru.osetrovev.graphqldemo.service.AuthorService;
import ru.osetrovev.graphqldemo.service.BookService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class GraphQlController {

    private final AuthorService authorService;
    private final BookService bookService;

    @QueryMapping
    public List<Book> getBooksByAuthor(@Argument AuthorDto author) {
        return bookService.getBooksByAuthor(author.getName());
    }

    @QueryMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @QueryMapping
    public Optional<Author> getAuthor(@Argument String name) {
        return authorService.getAuthor(name);
    }

    @MutationMapping
    public Book saveBook(@Argument String title, @Argument List<AuthorDto> authors) {
        return bookService.addBook(title, authors);
    }

    @MutationMapping
    public Author saveAuthor(@Argument String name, @Argument List<BookDto> books) {
        return authorService.addAuthor(name, books);
    }
}
