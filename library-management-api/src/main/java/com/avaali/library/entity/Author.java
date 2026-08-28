package com.avaali.library.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "author_name", nullable = false)
    private String name;

    @Column(name = "author_nationality")
    private String nationality;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books;
}
