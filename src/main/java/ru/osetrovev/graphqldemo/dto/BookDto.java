package ru.osetrovev.graphqldemo.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookDto {

    private String title;
    private List<AuthorDto> authors;
}
