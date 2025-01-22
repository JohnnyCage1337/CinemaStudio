package com.example.io_app.DTO;

public class FilmDTO {
    private String title;
    private String genre;
    private int duration;

    public FilmDTO(String title, String genre, int duration) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
    }

    public String getTitle() {
        return this.title;
    }

    public String getGenre() {
        return this.genre;
    }

    public int getDuration() {
        return this.duration;
    }
}
