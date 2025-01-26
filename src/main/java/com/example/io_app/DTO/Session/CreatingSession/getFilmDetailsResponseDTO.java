package com.example.io_app.DTO.Session.CreatingSession;

public class getFilmDetailsResponseDTO {
    private String filmTitle;
    private int filmDuration;

    public getFilmDetailsResponseDTO(int filmDuration, String filmTitle) {
        this.filmDuration = filmDuration;
        this.filmTitle = filmTitle;
    }

    public String getFilmTitle() {
        return filmTitle;
    }

    public int getFilmDuration() {
        return filmDuration;
    }
}
