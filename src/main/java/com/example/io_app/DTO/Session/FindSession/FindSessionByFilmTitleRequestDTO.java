package com.example.io_app.DTO.Session.FindSession;

public class FindSessionByFilmTitleRequestDTO {

    private String filmTitleFragment;

    public FindSessionByFilmTitleRequestDTO(String filmTitleFragment) {
        this.filmTitleFragment = filmTitleFragment;
    }

    public String getFilmTitleFragment() {
        return filmTitleFragment;
    }
}
