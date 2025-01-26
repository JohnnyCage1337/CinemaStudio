package com.example.io_app.DTO.Session.CreatingSession;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateSessionRequestDTO {

    private int filmID;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int roomNumber;
    private int totalSeats;
    private double price;

    public CreateSessionRequestDTO(
            int filmID,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime,
            int roomNumber,
            int totalSeats,
            double price
    )
    {
        this.filmID = filmID;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.roomNumber = roomNumber;
        this.totalSeats = totalSeats;
        this.price = price;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    // gettery / settery
    public LocalTime getStartTime() { return startTime; }
    public LocalDate getDate() { return date; }
    public int getRoomNumber() { return roomNumber; }
    public int getTotalSeats() { return totalSeats; }
    public double getPrice() { return price; }

    public void setDate(LocalDate date) { this.date = date; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public void setRoomNumber(int roomNumber) { this.roomNumber = roomNumber; }
    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }
    public void setPrice(double price) { this.price = price; }

    public int getFilmID() {
        return filmID;
    }

    public void setFilmID(int filmID) {
        this.filmID = filmID;
    }
}
