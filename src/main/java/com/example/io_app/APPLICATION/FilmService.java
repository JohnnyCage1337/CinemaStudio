package com.example.io_app.APPLICATION;

import com.example.io_app.DOMAIN.Film.Film;
import com.example.io_app.DOMAIN.Film.FilmManager;
import com.example.io_app.DOMAIN.Film.FilmValidator;
import com.example.io_app.DTO.Film.*;
import com.example.io_app.DTO.Session.CreateSession.getFilmDetailsRequestDTO;
import com.example.io_app.DTO.Session.CreateSession.getFilmDetailsResponseDTO;
import com.example.io_app.INFRASTRUCTURE.FilmRepository;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FilmService {

    private final FilmRepository filmRepository;
    private final FilmManager filmManager;

    public FilmService() {
        this.filmRepository = new FilmRepository();
        this.filmManager = new FilmManager();
    }

    public List<FilmDTO> getAllFilms() {
        return filmRepository.findAll()
                .stream()
                .map(film -> new FilmDTO(
                        film.getId(),
                        film.getTitle(),
                        film.getGenre(),
                        film.getDuration()))
                .collect(Collectors.toList());
    }

    public void createFilmUseCase(CreateFilmRequestDTO requestDTO) {

        FilmManager filmManager = new FilmManager();
        Film film;
        if(!isFilmAlreadyExist(requestDTO.getTitle())) {

            try {
                 film = filmManager.createFilm(requestDTO.getTitle(), requestDTO.getGenre(), requestDTO.getDuration());
            } catch(Exception e) {
                throw e;
            }
            if(!filmRepository.save(film)){
                throw new RuntimeException("Film could not be created");
            }
        }
        else{
            throw new IllegalArgumentException("Podany film jest już zapisany w bazie.");
        }

    }

    private boolean isFilmAlreadyExist(String title) {
        // Sprawdzenie w repozytorium, czy istnieje film o podanym tytule
        List<Film> foundFilms = filmRepository.findByTitle(title);

        // Jeśli lista jest pusta, zwracamy false (film nie istnieje)
        if (foundFilms.isEmpty()) {
            return false;
        }

        // Iteracja po znalezionych filmach i sprawdzenie tytułów funkcją isSameTitle
        for (Film film : foundFilms) {
            if (FilmValidator.isSameTitle(title, film.getTitle())) {
                return true; // Film istnieje, zwracamy true
            }
        }

        // Jeśli żaden film w liście nie spełnia warunku, zwracamy false
        return false;
    }

    public FindFilmResponseDTO findFilmUseCase(FindFilmRequestDTO requestDTO){

        List<Film> foundFilms = filmRepository.findByTitle(requestDTO.getTitle());

        List<FilmDTO> dtos = foundFilms.stream()
                .map(film -> new FilmDTO(
                        film.getId(),
                        film.getTitle(),
                        film.getGenre(),
                        film.getDuration()))
                .collect(Collectors.toList());


        return new FindFilmResponseDTO(dtos);
    }

    public FindFilmResponseDTO findFilmByIdUseCase(FindFilmRequestDTO requestDTO){

       Film film = filmRepository.findById(requestDTO.getId());
        FilmDTO dto = new FilmDTO(film.getId(), film.getTitle(), film.getGenre(), film.getDuration());
        return new FindFilmResponseDTO(dto);
    }

    public getFilmDetailsResponseDTO findFilmByIdUseCase(getFilmDetailsRequestDTO requestDTO) {
        if(!FilmValidator.validateId(requestDTO.getFilmId())){
            throw new IllegalArgumentException("ID filmu jest < 0");
        }
        Film film = filmRepository.findById(requestDTO.getFilmId());

        // Sprawdzenie, czy film istnieje
        if (film == null) {
            throw new IllegalArgumentException("Nie odnaleziono filmu o podanym ID: " + requestDTO.getFilmId());
        }

        // Zwrócenie poprawnego DTO, jeśli film istnieje
        return new getFilmDetailsResponseDTO(film.getDuration(), film.getTitle());
    }


    public DeleteFilmResponseDTO deleteFilmUseCase(DeleteFilmRequestDTO requestDTO){
        return new DeleteFilmResponseDTO(filmRepository.deleteByID(requestDTO.getFilmDTO().getId()));
    }

    public void updateFilmUseCase(UpdateFilmRequestDTO requestDTO) {
        // Pobranie filmu z repozytorium
        Film film = filmRepository.findByID(requestDTO.getMovieID());
        if (film == null) {
            throw new RuntimeException("Film not found");
        }

        // Sprawdzenie, czy nowy tytuł różni się od starego
        boolean isTitleChanged = !Objects.equals(requestDTO.getFilmTitle(), requestDTO.getNewFilmTitle());

        if (isTitleChanged && isFilmAlreadyExist(requestDTO.getNewFilmTitle())) {
            throw new IllegalArgumentException("Podany film jest już zapisany w bazie.");
        }

        // Aktualizacja danych filmu
        try {
            if (isTitleChanged) {
                filmManager.setTitle(film, requestDTO.getNewFilmTitle());
            }
            filmManager.setDuration(film, requestDTO.getNewDuration());
            filmManager.setGenre(film, requestDTO.getNewFilmGenre());
        } catch (Exception e) {
            throw new RuntimeException("Error updating film: " + e.getMessage(), e);
        }

        // Zapisanie zmian w repozytorium
        if (!filmRepository.updateFilm(film)) {
            throw new RuntimeException("Film update failed");
        }
    }



}
