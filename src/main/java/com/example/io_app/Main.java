package com.example.io_app;

import com.example.io_app.DOMAIN.Film.Film;
import com.example.io_app.INFRASTRUCTURE.FilmRepository;

public class Main {

    public static void main(String[] args) {

        FilmRepository filmRepository = new FilmRepository();

        for(Film film : filmRepository.findAll()){
            System.out.println(film);
        }

    }
}



