package com.example.io_app.APPLICATION;

import com.example.io_app.DOMAIN.Film;
import com.example.io_app.DOMAIN.FilmManager;
import com.example.io_app.DTO.FilmDTO;
import com.example.io_app.INFRASTRUCTURE.FilmRepository;


import java.util.List;
import java.util.stream.Collectors;

public class FilmService {
    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public List<FilmDTO> getFilmSummaries() {
        return filmRepository.findAll().stream()
                .map(film -> new FilmDTO(film.getTitle(), film.getGenre(), film.getDuration()))
                .collect(Collectors.toList());
    }

    public FilmDTO createAndSaveFilm(FilmDTO filmDTO) {
        Film film = new Film(
                filmDTO.getTitle(),
                filmDTO.getGenre(),
                filmDTO.getDuration()
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


    // Konwersja obiektu Film -> FilmDTO
    private FilmDTO convertToDTO(Film film) {
        return new FilmDTO(
                film.getTitle(),
                film.getGenre(),
                film.getDuration()
        );
    }
}
