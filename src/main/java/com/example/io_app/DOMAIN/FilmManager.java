package com.example.io_app.DOMAIN;

import java.util.ArrayList;
import java.util.List;

public class FilmManager {

    private static List<Film> films;

    public FilmManager(){

    }

    public static Film createFilm(String name, int duration, String description, String genre, String rate, String director, String language){
        Film film = new Film(name, duration, description, genre, rate, director, language);
        films.add(film);
        return film;
    }

    public static Film readFilmByName(String name) {
        return films.stream()
                .filter(film -> film.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public static List<Film> readAllFilms() {
        return new ArrayList<>(films);
    }

    public static boolean updateFilm(String name, int duration, String description, String genre, String rate, String director, String language) {
        Film film = readFilmByName(name);
        if (film != null) {
            film.setDuration(duration);
            film.setDescription(description);
            film.setGenre(genre);
            film.setRate(rate);
            film.setDirector(director);
            film.setLanguage(language);
            return true;
        }
        return false;
    }

    public static boolean deleteFilm(String name) {
        return films.removeIf(film -> film.getName().equalsIgnoreCase(name));
    }


}
