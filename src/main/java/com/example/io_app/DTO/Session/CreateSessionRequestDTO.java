package com.example.io_app.DTO.Session;

import java.time.LocalDateTime;
import java.util.Date;

public class CreateSessionRequestDTO {

    private int filmId;
    private Date date;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int roomNumber;
    private int availableSeats;
    private int totalSeats;
    private double price;

    public CreateSessionRequestDTO(int filmId,
                                   Date date,
                                   LocalDateTime startTime,
                                   LocalDateTime endTime,
                                   int roomNumber,
                                   int availableSeats,
                                   int totalSeats,
                                   double price)
    {
        this.filmId = filmId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.roomNumber = roomNumber;
        this.availableSeats = availableSeats;
        this.totalSeats = totalSeats;
        this.price = price;
    }

    // gettery / settery
    public int getFilmId() { return filmId; }
    public Date getDate() { return date; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public int getRoomNumber() { return roomNumber; }
    public int getAvailableSeats() { return availableSeats; }
    public int getTotalSeats() { return totalSeats; }
    public double getPrice() { return price; }

    public void setFilmId(int filmId) { this.filmId = filmId; }
    public void setDate(Date date) { this.date = date; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public void setRoomNumber(int roomNumber) { this.roomNumber = roomNumber; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }
    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }
    public void setPrice(double price) { this.price = price; }
}
