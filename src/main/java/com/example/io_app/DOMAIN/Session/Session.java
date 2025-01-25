package com.example.io_app.DOMAIN.Session;

import com.example.io_app.DOMAIN.Film.Film;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class Session {

    private int id;
    private int filmID;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int roomNumber;
    private int availableSeats;
    private int totalSeats;
    private double price;

    public Session(int filmID, LocalDate date, LocalTime startTime, int roomNumber, int totalSeats, double price) {
        this.filmID = filmID;
        this.date = date;
        this.startTime = startTime;
        this.endTime = null;//calculateEndTime();
        this.roomNumber = roomNumber;
        this.availableSeats = totalSeats;
        this.totalSeats = totalSeats;
        this.price = price;
    }

    public Session(int id, int filmID, LocalDate date, LocalTime startTime, LocalTime endTime, int roomNumber, int availableSeats, int totalSeats, double price) {
        this.id = id;
        this.filmID = filmID;
        this.date = date;
        this.startTime = startTime;
        this.endTime = null;//calculateEndTime();
        this.roomNumber = roomNumber;
        this.availableSeats = availableSeats;
        this.totalSeats = totalSeats;
        this.price = price;
    }

//    private LocalTime calculateEndTime() {
//        if (film != null && startTime != null) {
//            return startTime.plusMinutes(film.getDuration());
//        }
//        return null;
//    }

//    @Override
//    public String toString() {
//
//        DateTimeFormatter start = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")
//                .withLocale(new java.util.Locale("pl", "PL"));
//
//        DateTimeFormatter end = DateTimeFormatter.ofPattern("HH:mm");
//
//        return "Seans {" +
//                "id = " + id +
//                ", tytuł = " + film.getTitle() +
//                ", sala = " + roomNumber +
//                ", początek = " + startTime.format(start) +
//                ", koniec = " + endTime.format(end) +
//                ", cena = " + price +
//                ", dostępne miejsca = " + availableSeats +
//                ", wszystkie miejsca = " + totalSeats +
//                '}';
//    }

    // GETTERY I SETTERY


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFilmID() {
        return filmID;
    }

    public void setFilmID(int filmID) {
        this.filmID = filmID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
