package com.example.io_app.INFRASTRUCTURE;

import com.example.io_app.DOMAIN.Film.Film;
import com.example.io_app.DOMAIN.Session.Session;
import com.example.io_app.INFRASTRUCTURE.FilmRepository;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SessionRepository {

    private static final String URL = "jdbc:sqlite:dataRepository.db";
    private final FilmRepository filmRepository;

    // Format do zapisu/odczytu Date (tylko YYYY-MM-DD):
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    // Format do zapisu/odczytu LocalDateTime (np. YYYY-MM-DD HH:mm:ss):
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public SessionRepository() {
        this.filmRepository = new FilmRepository();  // użyjemy do findById(filmId)
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String createTableSql = """
            CREATE TABLE IF NOT EXISTS sessions (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                film_id INTEGER NOT NULL,
                show_date TEXT NOT NULL,      -- przechowujemy date np. '2025-01-30'
                start_time TEXT NOT NULL,     -- np. '2025-01-30 15:00:00'
                end_time TEXT NOT NULL,
                room_number INTEGER NOT NULL,
                available_seats INTEGER NOT NULL,
                total_seats INTEGER NOT NULL,
                price REAL NOT NULL,
                FOREIGN KEY (film_id) REFERENCES films(id)
            );
        """;

        try (Connection connection = DriverManager.getConnection(URL);
             Statement stmt = connection.createStatement()) {

            stmt.execute(createTableSql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Zapis nowego seansu
    public void save(Session session) {
        String insertSql = """
            INSERT INTO sessions 
            (film_id, show_date, start_time, end_time, room_number, 
             available_seats, total_seats, price)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?);
        """;

        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement pstmt = connection.prepareStatement(insertSql)) {

            // film_id (pobieramy z obiektu Film)
            pstmt.setInt(1, session.getFilm().getId());

            // show_date (java.util.Date -> String "yyyy-MM-dd")
            String dateStr = DATE_FORMAT.format(session.getDate());
            pstmt.setString(2, dateStr);

            // start_time i end_time (LocalDateTime -> String "yyyy-MM-dd HH:mm:ss")
            String startTimeStr = session.getStartTime().format(DATE_TIME_FORMATTER);
            pstmt.setString(3, startTimeStr);

            String endTimeStr = session.getEndTime().format(DATE_TIME_FORMATTER);
            pstmt.setString(4, endTimeStr);

            pstmt.setInt(5, session.getRoomNumber());
            pstmt.setInt(6, session.getAvailableSeats());
            pstmt.setInt(7, session.getTotalSeats());
            pstmt.setDouble(8, session.getPrice());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Pobranie wszystkich seansów (SELECT *)
    public List<Session> findAll() {
        List<Session> sessions = new ArrayList<>();
        String selectSql = "SELECT * FROM sessions;";

        try (Connection connection = DriverManager.getConnection(URL);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(selectSql)) {

            while (rs.next()) {
                // Odczytujemy kolumny
                int id = rs.getInt("id");
                int filmId = rs.getInt("film_id");
                String dateStr = rs.getString("show_date");
                String startTimeStr = rs.getString("start_time");
                String endTimeStr = rs.getString("end_time");
                int roomNumber = rs.getInt("room_number");
                int availableSeats = rs.getInt("available_seats");
                int totalSeats = rs.getInt("total_seats");
                double price = rs.getDouble("price");

                // Zamieniamy dateStr -> java.util.Date
                Date dateObj = null;
                try {
                    dateObj = DATE_FORMAT.parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // Zamieniamy start/end -> LocalDateTime
                LocalDateTime startTime = LocalDateTime.parse(startTimeStr, DATE_TIME_FORMATTER);
                LocalDateTime endTime = LocalDateTime.parse(endTimeStr, DATE_TIME_FORMATTER);

                // Odczyt Filmu z bazy
                Film film = filmRepository.findById(filmId);

                // Tworzymy obiekt Session
                Session session = new Session();
                session.setId(id);
                session.setFilm(film);
                session.setDate(dateObj);
                session.setStartTime(startTime);
                session.setEndTime(endTime);
                session.setRoomNumber(roomNumber);
                session.setAvailableSeats(availableSeats);
                session.setTotalSeats(totalSeats);
                session.setPrice(price);

                sessions.add(session);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessions;
    }

    // Pobranie seansu po ID
    public Session findById(int sessionId) {
        String selectSql = "SELECT * FROM sessions WHERE id = ?;";
        Session session = null;

        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement pstmt = connection.prepareStatement(selectSql)) {

            pstmt.setInt(1, sessionId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    int filmId = rs.getInt("film_id");
                    String dateStr = rs.getString("show_date");
                    String startTimeStr = rs.getString("start_time");
                    String endTimeStr = rs.getString("end_time");
                    int roomNumber = rs.getInt("room_number");
                    int availableSeats = rs.getInt("available_seats");
                    int totalSeats = rs.getInt("total_seats");
                    double price = rs.getDouble("price");

                    // Konwersja dateStr -> java.util.Date
                    Date dateObj = null;
                    try {
                        dateObj = DATE_FORMAT.parse(dateStr);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    LocalDateTime startTime = LocalDateTime.parse(startTimeStr, DATE_TIME_FORMATTER);
                    LocalDateTime endTime = LocalDateTime.parse(endTimeStr, DATE_TIME_FORMATTER);

                    Film film = filmRepository.findById(filmId);

                    session = new Session();
                    session.setId(id);
                    session.setFilm(film);
                    session.setDate(dateObj);
                    session.setStartTime(startTime);
                    session.setEndTime(endTime);
                    session.setRoomNumber(roomNumber);
                    session.setAvailableSeats(availableSeats);
                    session.setTotalSeats(totalSeats);
                    session.setPrice(price);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return session;
    }
}
