package com.example.io_app.DTO;


import com.example.io_app.DOMAIN.Film;

import java.util.List;

public interface FilmRepositiry {
    void save(Film movie);
    List<Film> findAll();
}