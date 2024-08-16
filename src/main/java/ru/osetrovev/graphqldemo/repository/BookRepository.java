package ru.osetrovev.graphqldemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.osetrovev.graphqldemo.entity.Author;
import ru.osetrovev.graphqldemo.entity.Book;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findAllByAuthors (Author author);
}
