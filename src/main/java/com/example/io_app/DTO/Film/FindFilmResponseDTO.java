package com.example.io_app.DTO.Film;

import java.util.List;

public class FindFilmResponseDTO {

    private List<FilmDTO> foundFilms;
    private FilmDTO foundFilm;

    public FindFilmResponseDTO(List<FilmDTO> dtos){
        this.foundFilms = dtos;
        this.foundFilm = null;
    }

    public FindFilmResponseDTO(FilmDTO dto){
        this.foundFilm = dto;
        this.foundFilms = null;
    }

    public List<FilmDTO> getFoundFilms() {
        return foundFilms;
    }

    public FilmDTO getFoundFilm() {return foundFilm;}
}
