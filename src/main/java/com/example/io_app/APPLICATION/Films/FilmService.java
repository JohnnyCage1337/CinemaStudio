package com.example.io_app.APPLICATION.Films;

import com.example.io_app.DOMAIN.Film;
import com.example.io_app.DTO.FilmDTO;
import com.example.io_app.DTO.FilmRepositiry;


import java.util.List;
import java.util.stream.Collectors;

public class FilmService {
    private final FilmRepositiry filmRepository;

    public FilmService(FilmRepositiry filmRepository) {
        this.filmRepository = filmRepository;
    }

    public List<FilmDTO> getFilmSummaries() {
        return filmRepository.findAll().stream()
                .map(film -> new FilmDTO(film.getName(), film.getGenre(), film.getDuration()))
                .collect(Collectors.toList());
    }
}
