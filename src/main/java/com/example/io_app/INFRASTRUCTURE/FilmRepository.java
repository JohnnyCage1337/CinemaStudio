package com.example.io_app.INFRASTRUCTURE;

import com.example.io_app.DOMAIN.Film.Film;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmRepository {

    private static final String URL = "jdbc:sqlite:dataRepository.db";

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
    public boolean save(Film film) {
        String insertSql = "INSERT INTO films (title, genre, duration) VALUES (?, ?, ?);";

        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement pstmt = connection.prepareStatement(insertSql)) {

            pstmt.setString(1, film.getTitle());
            pstmt.setString(2, film.getGenre());
            pstmt.setInt(3, film.getDuration());

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                int duration = rs.getInt("duration");

                Film film = new Film(id, title, genre, duration);
                films.add(film);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return films;
    }

    //Szukanie filmu po tytule
    public List<Film> findByTitle(String titleToFind) {
        String selectSql = "SELECT * FROM films WHERE LOWER(title) LIKE LOWER(?);";

        List<Film> films = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement pstmt = connection.prepareStatement(selectSql)) {

            pstmt.setString(1, "%" + titleToFind + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    String genre = rs.getString("genre");
                    int duration = rs.getInt("duration");

                    films.add(new Film(id, title, genre, duration));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return films;
    }

    public boolean deleteByID(int id) {
        String deleteSql = "DELETE FROM films WHERE id = ?;";
        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement pstmt = connection.prepareStatement(deleteSql)) {

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // jeśli > 0, to coś zostało usunięte

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateFilm(Film film) {
        String updateSql = "UPDATE films SET title = ?, genre = ?, duration = ? WHERE id = ?;";
        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement pstmt = connection.prepareStatement(updateSql)) {

            pstmt.setString(1, film.getTitle());
            pstmt.setString(2, film.getGenre());
            pstmt.setInt(3, film.getDuration());
            pstmt.setInt(4, film.getId());  // jeśli ID jest typu long

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Film findByID(int idToFind) {
        String selectSql = "SELECT * FROM films WHERE id = ?;";
        Film film = null;

        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement pstmt = connection.prepareStatement(selectSql)) {

            pstmt.setInt(1, idToFind);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    String genre = rs.getString("genre");
                    int duration = rs.getInt("duration");

                    film = new Film(id, title, genre, duration);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return film; // Zwraca znaleziony film lub null, jeśli brak wyniku
    }

    public Film findById(int idToFind) {
        String selectSql = "SELECT * FROM films WHERE id = ?;";

        Film film = null;

        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement pstmt = connection.prepareStatement(selectSql)) {

            pstmt.setInt(1, idToFind);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    String genre = rs.getString("genre");
                    int duration = rs.getInt("duration");

                    film = new Film(id, title, genre, duration);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return film;
    }

}