package com.example.io_app.DOMAIN;

import com.example.io_app.INFRASTRUCTURE.FilmRepository;

import java.util.ArrayList;
import java.util.List;

public class FilmManager {

    private FilmRepository filmRepository;

    public FilmManager(){
        filmRepository = new FilmRepository();
    }

    public Film createFilm(String title, String genre, int duration){
        Film film = new Film(title, genre, duration);
        return film;
    }

    public  Film readFilmByName(String title) {
        return filmRepository.getFilms().stream()
                .filter(film -> film.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    public List<Film> readAllFilms() {
        return new ArrayList<>(filmRepository.getFilms());
    }

    public boolean updateFilm(String title, int duration, String description, String genre, String rate, String director, String language) {
        Film film = readFilmByName(title);
        if (film != null) {
            film.setDuration(duration);
            film.setGenre(genre);
            return true;
        }
        return false;
    }

    public boolean deleteFilm(String title) {
        return filmRepository.getFilms().removeIf(film -> film.getTitle().equalsIgnoreCase(title));
    }


}
