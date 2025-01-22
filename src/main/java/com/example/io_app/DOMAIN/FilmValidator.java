package com.example.io_app.DOMAIN;

public class FilmValidator {
    public void validate(Film film) {
        if (film.getName() == null || film.getName().isEmpty()) {
            throw new IllegalArgumentException("Invalid movie title");
        }
        if (film.getGenre() == null || film.getGenre().isEmpty()) {
            throw new IllegalArgumentException("Invalid movie genre");
        }
        if (film.getDuration() <= 0) {
            throw new IllegalArgumentException("Duration must be greater than zero");
        }
    }
}
