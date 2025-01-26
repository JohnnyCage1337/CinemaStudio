package com.example.io_app.DOMAIN.Session;

import com.example.io_app.DOMAIN.Film.Film;
import com.example.io_app.INFRASTRUCTURE.SessionRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SessionManager {

    private SessionRepository sessionRepository;

    public SessionManager() {
        this.sessionRepository = new SessionRepository();
    }

    public Session createSession(int filmID, LocalDate date, LocalTime startTime, LocalTime endTime, int roomNumber, int totalSeats, double price
    ) {
        return new Session(filmID, date, startTime, endTime, roomNumber, totalSeats, price);
    }

    public Session readSessionById(int id) {
        return sessionRepository.findById(id);
    }

    public List<Session> readAllSessions() {
        return sessionRepository.findAll();
    }


}