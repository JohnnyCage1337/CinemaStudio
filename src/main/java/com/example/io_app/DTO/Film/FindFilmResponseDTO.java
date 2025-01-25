package com.example.io_app.DTO.Film;

import java.util.List;

public class FindFilmResponseDTO {

    private List<FilmDTO> foundFilms;

    public FindFilmResponseDTO(List<FilmDTO> dtos){
        this.foundFilms = dtos;
    }

    public List<FilmDTO> getFoundFilms() {
        return foundFilms;
    }
}
