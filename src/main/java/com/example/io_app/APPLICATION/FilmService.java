package com.example.io_app.APPLICATION;

import com.example.io_app.DOMAIN.Film;
import com.example.io_app.DOMAIN.FilmManager;
import com.example.io_app.DTO.CreatingFilmDTO;
import com.example.io_app.INFRASTRUCTURE.FilmRepository;


import java.util.List;
import java.util.stream.Collectors;

public class FilmService {
    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public List<CreatingFilmDTO> getFilmSummaries() {
        return filmRepository.findAll().stream()
                .map(film -> new CreatingFilmDTO(film.getTitle(), film.getGenre(), film.getDuration()))
                .collect(Collectors.toList());
    }

    public CreatingFilmDTO createAndSaveFilm(CreatingFilmDTO creatingFilmDTO) {
        Film film = new Film(
                creatingFilmDTO.getTitle(),
                creatingFilmDTO.getGenre(),
                creatingFilmDTO.getDuration()
        );

        FilmManager filmManager = new FilmManager();

        filmManager.createFilm(
                film.getTitle(),
                film.getGenre(),
                film.getDuration()
        );

        filmRepository.save(film);

        return convertToDTO(film);
    }

    public void findFilm(String filmTitleFragment){

        List<Film> foundFilms = filmRepository.findByTitle(filmTitleFragment);


    }

    public boolean deleteFilm(int id){
        return true;
    }


    // Konwersja obiektu Film -> CreatingFilmDTO
    private CreatingFilmDTO convertToDTO(Film film) {
        return new CreatingFilmDTO(
                film.getTitle(),
                film.getGenre(),
                film.getDuration()
        );
    }
}
