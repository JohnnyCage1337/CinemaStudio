package com.example.io_app.DOMAIN.Film;

public class FilmValidator {
    public static void validateId(int id) {
       if (id <= 0) {
           throw new IllegalArgumentException("Id musi być liczbą całkowitą > 0");
       }

    }

}
