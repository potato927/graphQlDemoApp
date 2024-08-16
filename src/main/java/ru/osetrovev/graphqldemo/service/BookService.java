package ru.osetrovev.graphqldemo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.osetrovev.graphqldemo.dto.AuthorDto;
import ru.osetrovev.graphqldemo.entity.Author;
import ru.osetrovev.graphqldemo.entity.Book;
import ru.osetrovev.graphqldemo.repository.AuthorRepository;
import ru.osetrovev.graphqldemo.repository.BookRepository;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
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

    @Transactional
    public Book addBook(String title, List<AuthorDto> authors) {
        Book book = Book.builder()
                .id(UUID.randomUUID())
                .title(title)
                .authors(authors.stream().map(a -> {
                    Optional<Author> existingAuthor = authorRepository.findAuthorByName(a.getName());
                    if (existingAuthor.isPresent()) {
                        return existingAuthor.get();
                    } else {
                        Author newAuthor = Author.builder()
                                .id(UUID.randomUUID())
                                .name(a.getName())
                                .build();
                        authorRepository.save(newAuthor);
                        return newAuthor;
                    }
                })
                        .collect(Collectors.toList()))
                .build();
        return bookRepository.saveAndFlush(book);
    }
}
