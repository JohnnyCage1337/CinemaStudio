package com.example.io_app.CONTRACTS;


import com.example.io_app.DOMAIN.Film.Film;

import java.util.List;

public interface FilmRepository {
    void save(Film movie);
    List<Film> findAll();
}