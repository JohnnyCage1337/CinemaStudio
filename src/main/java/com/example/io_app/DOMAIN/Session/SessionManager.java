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

    public SessionManager(){
        sessionRepository = new SessionRepository();
    }

    public static Session createSession(int filmID, LocalDate date, LocalTime startTime, int roomNumber, int totalSeats, double price
    ) {
        Session session = new Session(filmID, date, startTime, roomNumber, totalSeats, price);



        return session;
    }

    public static Session readSessionById(int id) {
        return sessions.stream()
                .filter(session -> session.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static List<Session> readAllSessions() {
        return new ArrayList<>(sessions);
    }

//    public static boolean updateSession(int id, Film film, int roomNumber, LocalTime startTime, double price, int totalSeats) {
//        Session session = readSessionById(id);
//        if (session != null) {
//            session.setFilm(film);
//            session.setRoomNumber(roomNumber);
//            session.setStartTime(startTime);
//            session.setPrice(price);
//            session.setTotalSeats(totalSeats);
//            session.setAvailableSeats(totalSeats);
//            return true;
//        }
//        return false;
//    }
//
//    public static boolean updateSessionFilm(int sessionID, Film film){
//        Session session = readSessionById(sessionID);
//        if (session != null) {
//            session.setFilm(film);
//            return true;
//        }
//        return false;
//    }
//
//    public static boolean deleteSession(int id) {
//        return sessions.removeIf(session -> session.getId() == id);
//    }
//
//
//    public static List<Session> findSessionsByFilmTitle(String title) {
//        return sessions.stream()
//                .filter(session -> session.getFilm().getTitle().equalsIgnoreCase(title))
//                .collect(Collectors.toList());
//    }
//
//    public static List<Session> findSessionsInTimeRange(LocalTime from, LocalTime to) {
//        return sessions.stream()
//                .filter(session -> !session.getStartTime().isBefore(from)
//                        && !session.getStartTime().isAfter(to))
//                .collect(Collectors.toList());
//    }
//
//    public static int getAvailableSeats(int sessionId) {
//        Session session = readSessionById(sessionId);
//        return (session != null) ? session.getAvailableSeats() : 0;
//    }
//
//    public static boolean hasAvailableSeats(int sessionId) {
//        Session session = readSessionById(sessionId);
//        return session != null && session.getAvailableSeats() > 0;
//    }
//
//    public static List<Session> getSessionsSortedByStartTime() {
//        return sessions.stream()
//                .sorted(Comparator.comparing(Session::getStartTime))
//                .collect(Collectors.toList());
//    }
//
//    public static List<Session> getSessionsSortedByPrice() {
//        return sessions.stream()
//                .sorted(Comparator.comparingDouble(Session::getPrice))
//                .collect(Collectors.toList());
//    }
}
