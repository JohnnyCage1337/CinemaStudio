package com.example.io_app.DOMAIN.Film;

import com.example.io_app.INFRASTRUCTURE.FilmRepository;

import java.util.ArrayList;
import java.util.List;

public class FilmManager {

    private FilmRepository filmRepository;

    public FilmManager(){
        filmRepository = new FilmRepository();
    }

    public Film createFilm(String title, String genre, int duration){
        return new Film(title, genre, duration);
    }

    public  Film readFilmByName(String title) {
        return filmRepository.findAll().stream()
                .filter(film -> film.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    public List<Film> readAllFilms() {
        return filmRepository.findAll();
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

    public boolean deleteFilm(int id) {
        return filmRepository.deleteByID(id);
    }
}
