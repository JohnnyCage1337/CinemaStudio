package com.example.io_app;

import com.example.io_app.DOMAIN.Film;
import com.example.io_app.DOMAIN.Session;
import com.example.io_app.DOMAIN.SessionManager;
import com.example.io_app.INFRASTRUCTURE.FilmRepository;

import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        FilmRepository filmRepository = new FilmRepository();

        for(Film film : filmRepository.getFilms()){
            System.out.println(film);
        }

    }
}



