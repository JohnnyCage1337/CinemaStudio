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
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getDuration() {
        return duration;
    }
}
