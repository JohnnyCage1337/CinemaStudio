package com.example.io_app.APPLICATION;

import com.example.io_app.DOMAIN.Film.Film;
import com.example.io_app.DOMAIN.Film.FilmManager;
import com.example.io_app.DTO.Film.*;
import com.example.io_app.INFRASTRUCTURE.FilmRepository;


import java.util.List;
import java.util.stream.Collectors;

public class FilmService {

    private final FilmRepository filmRepository;
    private final FilmManager filmManager;

    public FilmService() {
        this.filmRepository = new FilmRepository();
        this.filmManager = new FilmManager();
    }

    public List<FilmDTO> getAllFilms() {
        return filmRepository.findAll()
                .stream()
                .map(film -> new FilmDTO(
                        film.getId(),
                        film.getTitle(),
                        film.getGenre(),
                        film.getDuration()))
                .collect(Collectors.toList());
    }

    public void createFilmUseCase(CreateFilmRequestDTO requestDTO) {
        Film film = new Film(
                requestDTO.getTitle(),
                requestDTO.getGenre(),
                requestDTO.getDuration()
        );

        FilmManager filmManager = new FilmManager();

        filmManager.createFilm(
                film.getTitle(),
                film.getGenre(),
                film.getDuration()
        );

        filmRepository.save(film);
    }

    public FindFilmResponseDTO findFilmUseCase(FindFilmRequestDTO requestDTO){

        List<Film> foundFilms = filmRepository.findByTitle(requestDTO.getTitle());

        List<FilmDTO> dtos = foundFilms.stream()
                .map(film -> new FilmDTO(
                        film.getId(),
                        film.getTitle(),
                        film.getGenre(),
                        film.getDuration()))
                .collect(Collectors.toList());

        return new FindFilmResponseDTO(dtos);
    }

    public DeleteFilmResponseDTO deleteFilmUseCase(DeleteFilmRequestDTO requestDTO){
        return new DeleteFilmResponseDTO(filmRepository.deleteByID(requestDTO.getFilmDTO().getId()));
    }

    public void updateFilmUseCase(UpdateFilmRequestDTO requestDTO) {
        Film film = filmRepository.findByID(requestDTO.getMovieID());
        if(film == null) {
            throw new RuntimeException("Film not found");
        }

        film.setTitle(requestDTO.getNewFilmTitle());
        film.setGenre(requestDTO.getNewFilmGenre());
        film.setDuration(requestDTO.getNewDuration());

        filmRepository.updateFilm(film);
    }
}
