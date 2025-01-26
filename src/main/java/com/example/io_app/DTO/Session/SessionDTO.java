package com.example.io_app.DTO.Session;

import java.time.LocalDate;
import java.time.LocalTime;

public class SessionDTO {

    private int sessionId;
    private String filmTitle;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int place;
    private int availableSeats;
    private int allSeats;
    private double price;

    public SessionDTO(int sessionId,
                      String filmTitle,
                      LocalDate date,
                      LocalTime startTime,
                      LocalTime endTime,
                      int place,
                      int availableSeats,
                      int allSeats,
                      double price) {
        this.sessionId = sessionId;
        this.filmTitle = filmTitle;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.place = place;
        this.availableSeats = availableSeats;
        this.allSeats = allSeats;
        this.price = price;
    }

    public int getSessionId() {
        return sessionId;
    }

    public String getFilmTitle() {
        return filmTitle;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public int getPlace() {
        return place;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public int getAllSeats() {
        return allSeats;
    }

    public double getPrice() {
        return price;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public void setFilmTitle(String filmTitle) {
        this.filmTitle = filmTitle;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public void setAllSeats(int allSeats) {
        this.allSeats = allSeats;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
