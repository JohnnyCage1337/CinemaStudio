package com.example.io_app.INFRASTRUCTURE;

import com.example.io_app.DOMAIN.Film;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmRepository {

    private static final String URL = "jdbc:sqlite:filmRepository.db";

    public FilmRepository() {
        createTableIfNotExists();

    }

    private void createTableIfNotExists() {
        String createTableSql = """
            CREATE TABLE IF NOT EXISTS films (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL,
                genre TEXT NOT NULL,
                duration INTEGER NOT NULL
            );
        """;

        try (Connection connection = DriverManager.getConnection(URL);
             Statement stmt = connection.createStatement()) {

            stmt.execute(createTableSql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Zapisywanie nowego filmu do bazy (INSERT)
    public void save(Film film) {
        String insertSql = "INSERT INTO films (title, genre, duration) VALUES (?, ?, ?);";

        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement pstmt = connection.prepareStatement(insertSql)) {

            pstmt.setString(1, film.getTitle());
            pstmt.setString(2, film.getGenre());
            pstmt.setInt(3, film.getDuration());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Pobieranie wszystkich filmów z bazy (SELECT *)
    public List<Film> findAll() {
        List<Film> films = new ArrayList<>();
        String selectSql = "SELECT * FROM films;";

        try (Connection connection = DriverManager.getConnection(URL);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(selectSql)) {

            while (rs.next()) {
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                int duration = rs.getInt("duration");

                Film film = new Film(title, genre, duration);
                films.add(film);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return films;
    }

    // (Opcjonalnie) Szukanie filmu po tytule
    public List<Film> findByTitle(String titleToFind) {
        String selectSql = "SELECT * FROM films WHERE LOWER(title) LIKE LOWER(?);";

        List<Film> films = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement pstmt = connection.prepareStatement(selectSql)) {

            pstmt.setString(1, "%" + titleToFind + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String title = rs.getString("title");
                    String genre = rs.getString("genre");
                    int duration = rs.getInt("duration");

                    films.add(new Film(title, genre, duration));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return films;
    }

    // (Opcjonalnie) Usuwanie filmu po tytule
    public boolean deleteByTitle(String titleToDelete) {
        String deleteSql = "DELETE FROM films WHERE LOWER(title) = LOWER(?);";
        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement pstmt = connection.prepareStatement(deleteSql)) {

            pstmt.setString(1, titleToDelete);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // jeśli > 0, to coś zostało usunięte

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // (Opcjonalnie) Update – np. zmiana gatunku i/lub czasu trwania
    public boolean updateFilm(String title, String newGenre, int newDuration) {
        String updateSql = "UPDATE films SET genre = ?, duration = ? WHERE LOWER(title) = LOWER(?);";
        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement pstmt = connection.prepareStatement(updateSql)) {

            pstmt.setString(1, newGenre);
            pstmt.setInt(2, newDuration);
            pstmt.setString(3, title);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}