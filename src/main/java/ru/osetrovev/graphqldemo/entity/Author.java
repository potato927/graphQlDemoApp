package ru.osetrovev.graphqldemo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "author")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    @Id
    private UUID id;
    private String name;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "author_book",
            joinColumns = {@JoinColumn(name = "author_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")}
    )
    @Fetch(FetchMode.SUBSELECT)
    private List<Book> books;
}