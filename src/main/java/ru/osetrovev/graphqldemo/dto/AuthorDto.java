package ru.osetrovev.graphqldemo.dto;

import lombok.Data;

import java.util.List;

@Data
public class AuthorDto {

    private String name;
    private List<BookDto> books;
}
