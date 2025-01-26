package com.example.io_app.DTO.Session;

public class SessionDTO {

    private int sessionId;
    private String filmTitle;
    private String date;
    private String startTime;
    private String endTime;
    private int place;
    private int availableSeats;
    private int allSeats;
    private double price;

    public SessionDTO(int sessionId,
                      String filmTitle,
                      String date,
                      String startTime,
                      String endTime,
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

    // --- GETTERY i SETTERY w konwencji JavaBeans (getXxx / setXxx) ---

    public int getSessionID() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public String getFilmTitle() {
        return filmTitle;
    }

    public void setFilmTitle(String filmTitle) {
        this.filmTitle = filmTitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public int getAllSeats() {
        return allSeats;
    }

    public void setAllSeats(int allSeats) {
        this.allSeats = allSeats;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSessionId() {
        return sessionId;
    }
}
