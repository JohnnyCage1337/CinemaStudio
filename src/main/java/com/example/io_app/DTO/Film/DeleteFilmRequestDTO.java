package com.example.io_app.DTO.Film;

public class DeleteFilmRequestDTO {

    private FilmDTO filmDTO;

    public FilmDTO getFilmDTO() {
        return filmDTO;
    }

    public void setFilmDTO(FilmDTO toDelete) {
        this.filmDTO = toDelete;
    }

    public DeleteFilmRequestDTO(FilmDTO toDelete){
        this.filmDTO=toDelete;
    }
}
