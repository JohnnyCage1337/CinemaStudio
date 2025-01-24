package com.example.io_app.DTO;

import java.util.List;

public class FindingFilmResponseDTO {

    private List<FilmDTO> foundFilms;

    public FindingFilmResponseDTO(List<FilmDTO> dtos){
        this.foundFilms = dtos;
    }

    public List<FilmDTO> getFoundFilms() {
        return foundFilms;
    }
}
