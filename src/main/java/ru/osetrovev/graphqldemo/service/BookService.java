package ru.osetrovev.graphqldemo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.osetrovev.graphqldemo.dto.AuthorDto;
import ru.osetrovev.graphqldemo.entity.Author;
import ru.osetrovev.graphqldemo.entity.Book;
import ru.osetrovev.graphqldemo.repository.BookRepository;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByAuthor(String name) {
        Optional<Author> author = authorService.getAuthor(name);
        if (author.isPresent()) {
            return bookRepository.findAllByAuthors(author.get());
        } else {
            log.debug("Автор {} не найден", name);
            return Collections.EMPTY_LIST;
        }
    }

    public Book addBook(String title, List<AuthorDto> authors) {
        Book book = Book.builder()
                .id(UUID.randomUUID())
                .title(title)
                .authors(authors.stream().map(a -> Author
                        .builder()
                        .id(getId(a))
                        .name(a.getName())
                        .build())
                        .collect(Collectors.toList()))
                .build();
        log.info("try to add Book {}", book);
        return bookRepository.saveAndFlush(book);
    }

    private UUID getId(AuthorDto author) {
        return authorService.getAuthor(author.getName()).isEmpty() ? UUID.randomUUID() : authorService.getAuthor(author.getName()).get().getId();
    }
}
