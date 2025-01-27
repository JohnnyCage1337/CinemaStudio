package com.example.io_app;

import com.example.io_app.DOMAIN.Film.Film;
import com.example.io_app.INFRASTRUCTURE.FilmRepository;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        FilmRepository filmRepository = new FilmRepository();

        // Lista prawdziwych tytułów filmów wraz z gatunkami i czasem trwania
        List<Film> films = Arrays.asList(
                new Film(0, "The Shawshank Redemption", "Drama", 142),
                new Film(0, "The Godfather", "Crime", 175),
                new Film(0, "The Dark Knight", "Action", 152),
                new Film(0, "Pulp Fiction", "Crime", 154),
                new Film(0, "The Lord of the Rings: The Return of the King", "Fantasy", 201),
                new Film(0, "Forrest Gump", "Drama", 142),
                new Film(0, "Inception", "Sci-Fi", 148),
                new Film(0, "The Matrix", "Sci-Fi", 136),
                new Film(0, "Fight Club", "Drama", 139),
                new Film(0, "Interstellar", "Sci-Fi", 169),
                new Film(0, "The Green Mile", "Drama", 189),
                new Film(0, "The Lion King", "Animation", 88),
                new Film(0, "Gladiator", "Action", 155),
                new Film(0, "The Silence of the Lambs", "Thriller", 118),
                new Film(0, "Saving Private Ryan", "War", 169),
                new Film(0, "Schindler's List", "Drama", 195),
                new Film(0, "Parasite", "Thriller", 132),
                new Film(0, "Avengers: Endgame", "Action", 181),
                new Film(0, "Whiplash", "Drama", 106),
                new Film(0, "The Prestige", "Drama", 130),
                new Film(0, "The Departed", "Crime", 151),
                new Film(0, "Django Unchained", "Western", 165),
                new Film(0, "The Social Network", "Drama", 120),
                new Film(0, "Joker", "Crime", 122),
                new Film(0, "1917", "War", 119),
                new Film(0, "Mad Max: Fury Road", "Action", 120),
                new Film(0, "Coco", "Animation", 105),
                new Film(0, "Shutter Island", "Thriller", 138),
                new Film(0, "The Grand Budapest Hotel", "Comedy", 99),
                new Film(0, "The Wolf of Wall Street", "Biography", 180),
                new Film(0, "La La Land", "Musical", 128),
                new Film(0, "Moonlight", "Drama", 111),
                new Film(0, "Inside Out", "Animation", 95),
                new Film(0, "Toy Story", "Animation", 81),
                new Film(0, "Up", "Animation", 96),
                new Film(0, "Braveheart", "History", 178),
                new Film(0, "A Beautiful Mind", "Biography", 135),
                new Film(0, "Slumdog Millionaire", "Drama", 120),
                new Film(0, "The Pursuit of Happyness", "Drama", 117),
                new Film(0, "Black Panther", "Action", 134),
                new Film(0, "Inglourious Basterds", "War", 153),
                new Film(0, "The Avengers", "Action", 143),
                new Film(0, "Iron Man", "Action", 126),
                new Film(0, "The Incredibles", "Animation", 115),
                new Film(0, "Frozen", "Animation", 102),
                new Film(0, "Finding Nemo", "Animation", 100),
                new Film(0, "Ratatouille", "Animation", 111),
                new Film(0, "The Good, the Bad and the Ugly", "Western", 161),
                new Film(0, "Se7en", "Crime", 127)
        );

        // Iteracja i zapis filmów do bazy danych
        for (Film film : films) {
            filmRepository.save(film);
            System.out.println("Dodano film: " + film);
        }
    }
}



