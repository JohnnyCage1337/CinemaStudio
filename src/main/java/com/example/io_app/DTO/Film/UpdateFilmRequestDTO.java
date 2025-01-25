package com.example.io_app.DTO.Film;

public class UpdateFilmRequestDTO {
    private int movieID;
    private String newFilmTitle;
    private String newFilmGenre;
    private int newDuration;

    public UpdateFilmRequestDTO(int movieID, String newFilmTitle, String newFilmGenre, int newDuration)
    {
        this.movieID = movieID;
        this.newFilmTitle = newFilmTitle;
        this.newFilmGenre = newFilmGenre;
        this.newDuration = newDuration;
    }

    public int getMovieID() {
        return movieID;
    }

    public String getNewFilmTitle() {
        return newFilmTitle;
    }

    public String getNewFilmGenre() {
        return newFilmGenre;
    }

    public int getNewDuration() {
        return newDuration;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public void setNewFilmTitle(String newFilmTitle) {
        this.newFilmTitle = newFilmTitle;
    }

    public void setNewFilmGenre(String newFilmGenre) {
        this.newFilmGenre = newFilmGenre;
    }

    public void setNewDuration(int newDuration) {
        this.newDuration = newDuration;
    }
}