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
@Table(name = "book")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    private UUID id;
    private String title;
    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "author_book",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "author_id")}
    )
    @Fetch(FetchMode.SUBSELECT)
    private List<Author> authors;
}