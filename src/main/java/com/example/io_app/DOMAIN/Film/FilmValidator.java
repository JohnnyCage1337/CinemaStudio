package com.example.io_app.DOMAIN.Film;

import java.util.Arrays;
import java.util.List;

public class FilmValidator {
    // Lista dozwolonych gatunków
    private static final List<String> ALLOWED_GENRES = Arrays.asList(
            "Akcja",
            "Komedia",
            "Horror",
            "Thriller",
            "Dramat",
            "Animacja"
    );

    // Walidacja ID
    public static boolean validateId(int id) {
        return id >= 0;
    }

    // Sprawdzanie poprawności czasu trwania
    public static boolean isDurationCorrect(int duration) {
        return duration > 0;
    }

    // Sprawdzanie poprawności gatunku
    public static boolean isGenreCorrect(String genre) {
        return genre != null && ALLOWED_GENRES.contains(genre);
    }

    // Porównanie tytułów (ignorując wielkość liter)
    public static boolean isSameTitle(String title, String otherTitle) {
        if (title == null || otherTitle == null) {
            return false;
        }
        return title.equalsIgnoreCase(otherTitle);
    }
}
