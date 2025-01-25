package com.example.io_app.INFRASTRUCTURE;

import com.example.io_app.DOMAIN.Film.Film;
import com.example.io_app.DOMAIN.Session.Session;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class SessionRepository {

    private static final String URL = "jdbc:sqlite:dataRepository.db";
    private final FilmRepository filmRepository;

    // Format do zapisu/odczytu samej daty, np. "2025-01-30"
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    // Format do zapisu/odczytu samego czasu, np. "16:30"
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public SessionRepository() {
        this.filmRepository = new FilmRepository();  // użyjemy do findByID(filmId)
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String createTableSql = """
            CREATE TABLE IF NOT EXISTS sessions (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                film_id INTEGER NOT NULL,
                show_date TEXT NOT NULL,      -- yyyy-MM-dd
                start_time TEXT NOT NULL,     -- HH:mm'
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

            // 1) film_id (pobieramy z session.getFilmID())
            pstmt.setInt(1, session.getFilmID());

            // 2) show_date (LocalDate -> String "yyyy-MM-dd")
            String dateStr = session.getDate().format(DATE_FORMATTER);
            pstmt.setString(2, dateStr);

            // 3) start_time (LocalTime -> String "HH:mm")
            // zakładam, że startTime != null
            String startTimeStr = session.getStartTime().format(TIME_FORMATTER);
            pstmt.setString(3, startTimeStr);

            // 4) end_time (LocalTime -> String "HH:mm")
            // Co jeśli endTime = null? Wstawiamy np. pusty string albo jakiś placeholder
            String endTimeStr = "";
            if (session.getEndTime() != null) {
                endTimeStr = session.getEndTime().format(TIME_FORMATTER);
            }
            pstmt.setString(4, endTimeStr);

            // 5) room_number
            pstmt.setInt(5, session.getRoomNumber());

            // 6) available_seats
            pstmt.setInt(6, session.getAvailableSeats());

            // 7) total_seats
            pstmt.setInt(7, session.getTotalSeats());

            // 8) price
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

                // Konwersja dateStr -> LocalDate
                LocalDate date = null;
                try {
                    date = LocalDate.parse(dateStr, DATE_FORMATTER);
                } catch (DateTimeParseException e) {
                    e.printStackTrace();
                }

                // Konwersja start/end -> LocalTime
                LocalTime startTime = null;
                LocalTime endTime = null;
                try {
                    startTime = LocalTime.parse(startTimeStr, TIME_FORMATTER);
                    endTime   = LocalTime.parse(endTimeStr, TIME_FORMATTER);
                } catch (DateTimeParseException e) {
                    e.printStackTrace();
                }

                // Tworzymy obiekt Session
                Session session = new Session(
                        id,
                        filmId,
                        date,
                        startTime,
                        endTime,
                        roomNumber,
                        availableSeats,
                        totalSeats,
                        price
                );

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

                    // Konwersja dateStr -> LocalDate
                    LocalDate dateObj = null;
                    try {
                        dateObj = LocalDate.parse(dateStr, DATE_FORMATTER);
                    } catch (DateTimeParseException e) {
                        e.printStackTrace();
                    }

                    // Konwersja start/end -> LocalTime
                    LocalTime startTime = null;
                    LocalTime endTime = null;
                    try {
                        startTime = LocalTime.parse(startTimeStr, TIME_FORMATTER);
                        endTime   = LocalTime.parse(endTimeStr, TIME_FORMATTER);
                    } catch (DateTimeParseException e) {
                        e.printStackTrace();
                    }

                    // Tutaj nie pobieramy już obiektu Film, bo Session ma filmID
                    // Film film = filmRepository.findByID(filmId); // usuwamy/komentujemy

                    // Tworzymy obiekt Session, korzystając z konstruktora
                    // lub seterów. Zakładam, że masz konstruktor z wszystkimi polami:
                    session = new Session(
                            id,
                            filmId,
                            dateObj,
                            startTime,
                            endTime,
                            roomNumber,
                            availableSeats,
                            totalSeats,
                            price
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return session;
    }

}
