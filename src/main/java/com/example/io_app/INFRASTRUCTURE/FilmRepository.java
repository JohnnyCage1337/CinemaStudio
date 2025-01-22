package com.example.io_app.INFRASTRUCTURE;

import com.example.io_app.DOMAIN.Film;

import java.util.ArrayList;
import java.util.List;

public class FilmRepository implements com.example.io_app.CONTRACTS.FilmRepository {

    private static final List<Film> films = new ArrayList<>();

    public FilmRepository(){
    }

    @Override
    public void save(Film film) {
        this.films.add(film);
    }

    @Override
    public List<Film> findAll() {
        return new ArrayList<>(this.films); // Zwracanie kopii listy
    }

    public List<Film> getFilms() {
        return this.films;
    }
}
