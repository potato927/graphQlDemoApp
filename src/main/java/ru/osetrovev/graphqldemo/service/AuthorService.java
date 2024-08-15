package ru.osetrovev.graphqldemo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.osetrovev.graphqldemo.dto.BookDto;
import ru.osetrovev.graphqldemo.entity.Author;
import ru.osetrovev.graphqldemo.entity.Book;
import ru.osetrovev.graphqldemo.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional
    public Author addAuthor(String name, List<BookDto> books) {
        Optional<Author> author = authorRepository.findAuthorByName(name);
        if (author.isPresent()) {
            author.get().getBooks().addAll(books.stream().map(b -> Book
                        .builder()
                        .id(UUID.randomUUID())
                        .title(b.getTitle())
                        .build())
                    .collect(Collectors.toList()));
            return authorRepository.saveAndFlush(author.get());
        } else {
            Author newAuthor = Author.builder()
                    .id(UUID.randomUUID())
                    .name(name)
                    .books(books.stream().map(b -> Book
                            .builder()
                            .id(UUID.randomUUID())
                            .title(b.getTitle())
                            .build())
                            .collect(Collectors.toList()))
                    .build();
            return authorRepository.saveAndFlush(newAuthor);
        }
    }

    public Optional<Author> getAuthor(String name) {

        return authorRepository.findAuthorByName(name);
    }

}
