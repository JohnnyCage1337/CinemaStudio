package com.example.io_app.APPLICATION;

import com.example.io_app.DOMAIN.Film;
import com.example.io_app.DOMAIN.FilmManager;
import com.example.io_app.DTO.CreatingFilmDTO;
import com.example.io_app.DTO.FilmDTO;
import com.example.io_app.DTO.FindingFilmRequestDTO;
import com.example.io_app.DTO.FindingFilmResponseDTO;
import com.example.io_app.INFRASTRUCTURE.FilmRepository;


import java.util.List;
import java.util.stream.Collectors;

public class FilmService {
    private final FilmRepository filmRepository;

    public FilmService() {
        FilmRepository filmRepository = new FilmRepository();
        this.filmRepository = filmRepository;
    }

    public List<CreatingFilmDTO> getFilmSummaries() {
        return filmRepository.findAll().stream()
                .map(film -> new CreatingFilmDTO(film.getTitle(), film.getGenre(), film.getDuration()))
                .collect(Collectors.toList());
    }

    public List<FilmDTO> getAllFilms() {
        return filmRepository.findAll()
                .stream()
                .map(film -> new FilmDTO(
                        film.getTitle(),
                        film.getGenre(),
                        film.getDuration()))
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

    public FindingFilmResponseDTO findFilmUseCase(FindingFilmRequestDTO requestDTO){

        List<Film> foundFilms = filmRepository.findByTitle(requestDTO.getTitle());

        List<FilmDTO> dtos = foundFilms.stream()
                .map(film -> new FilmDTO(film.getTitle(), film.getGenre(), film.getDuration()))
                .collect(Collectors.toList());

        return new FindingFilmResponseDTO(dtos);
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
