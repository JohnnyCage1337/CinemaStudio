package com.example.io_app.DOMAIN.Film;

import com.example.io_app.INFRASTRUCTURE.FilmRepository;

import java.util.ArrayList;
import java.util.List;

public class FilmManager {

    private FilmRepository filmRepository;

    public FilmManager(){
        filmRepository = new FilmRepository();
    }

    public Film createFilm(String title, String genre, int duration){
        if(!FilmValidator.isDurationCorrect(duration)){
            throw new IllegalArgumentException("Duration is incorrect");
        }
        if(!FilmValidator.isGenreCorrect(genre)){
            throw new IllegalArgumentException("Genre is incorrect");
        }
        return new Film(title, genre, duration);
    }


    public void setTitle(Film film,String title){
        //walidacja domenowa tytulu
        film.setTitle(title);

    }

    public void setDuration(Film film,int duration){
        if(!FilmValidator.isDurationCorrect(duration)){
            throw new IllegalArgumentException("Duration is incorrect");
        }
        film.setDuration(duration);

    }

    public void setGenre(Film film,String genre){
        if(!FilmValidator.isGenreCorrect(genre)){
            throw new IllegalArgumentException("Genre is incorrect");
        }
        film.setGenre(genre);
    }

}
