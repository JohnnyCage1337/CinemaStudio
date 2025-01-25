package com.example.io_app.DOMAIN.Session;

import com.example.io_app.DOMAIN.Film.Film;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Session {

    private int id;
    private Film film;
    private int roomNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double price;
    private int availableSeats;
    private int totalSeats;

    public Session() {
    }

    public Session(int id, Film film, int roomNumber, LocalDateTime startTime, double price, int totalSeats) {
        this.id = id;
        this.film = film;
        this.roomNumber = roomNumber;
        this.startTime = startTime;
        this.endTime = calculateEndTime();
        this.price = price;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
    }

    private LocalDateTime calculateEndTime() {
        if (film != null && startTime != null) {
            return startTime.plusMinutes(film.getDuration());
        }
        return null;
    }

    @Override
    public String toString() {

        DateTimeFormatter start = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")
                .withLocale(new java.util.Locale("pl", "PL"));

        DateTimeFormatter end = DateTimeFormatter.ofPattern("HH:mm");

        return "Seans {" +
                "id = " + id +
                ", tytuł = " + film.getTitle() +
                ", sala = " + roomNumber +
                ", początek = " + startTime.format(start) +
                ", koniec = " + endTime.format(end) +
                ", cena = " + price +
                ", dostępne miejsca = " + availableSeats +
                ", wszystkie miejsca = " + totalSeats +
                '}';
    }

    // GETTERY I SETTERY

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
        this.endTime = calculateEndTime();
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        this.endTime = calculateEndTime();
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }
}
