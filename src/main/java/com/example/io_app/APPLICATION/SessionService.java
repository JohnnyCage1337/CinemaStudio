package com.example.io_app.APPLICATION;

import com.example.io_app.DOMAIN.Film.Film;
import com.example.io_app.DOMAIN.Session.Session;
import com.example.io_app.DTO.Session.CreateSessionRequestDTO;
import com.example.io_app.DTO.Session.SessionDTO;
import com.example.io_app.INFRASTRUCTURE.FilmRepository;
import com.example.io_app.INFRASTRUCTURE.SessionRepository;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class SessionService {

    private final SessionRepository sessionRepository;
    private final FilmRepository filmRepository;

    public SessionService() {
        this.sessionRepository = new SessionRepository();
        this.filmRepository = new FilmRepository();
    }

    public void createSessionUseCase(CreateSessionRequestDTO requestDTO) {

        Session session = new Session(
                requestDTO.getFilmID(),
                requestDTO.getDate(),
                requestDTO.getStartTime(),
                requestDTO.getRoomNumber(),
                requestDTO.getTotalSeats(),
                requestDTO.getPrice()
        );

        //dodać obiekt session do session repository


        Film film = filmRepository.findByID(requestDTO.getFilmID());
        if (film == null) {
            throw new RuntimeException("Nie znaleziono filmu o ID: " + requestDTO.getFilmID());
        }
//
//        Session session = new Session();
//        session.setFilm(film);
//        session.setDate(requestDTO.getDate());
//        session.setStartTime(requestDTO.getStartTime());
//        session.setEndTime(requestDTO.getEndTime());
//        session.setRoomNumber(requestDTO.getRoomNumber());
//        session.setAvailableSeats(requestDTO.getAvailableSeats());
//        session.setTotalSeats(requestDTO.getTotalSeats());
//        session.setPrice(requestDTO.getPrice());
//
//        sessionRepository.save(session);
    }



    public List<SessionDTO> getAllSessions() {
        // Pobieramy wszystkie sesje z repozytorium
        return sessionRepository.findAll()
                .stream()
                .map(session -> {
                    // Konwersja Date -> String "yyyy-MM-dd"
                    String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(session.getDate());

                    // Konwersja LocalDateTime -> String "HH:mm"
                    String startTimeStr = session.getStartTime()
                            .format(DateTimeFormatter.ofPattern("HH:mm"));
                    String endTimeStr = session.getEndTime()
                            .format(DateTimeFormatter.ofPattern("HH:mm"));

                    // Tworzymy obiekt SessionDTO
                    return new SessionDTO(
                            session.getId(),                  // sessionId
                            filmRepository.findByID(session.getFilmID()).getTitle(), //title
                            dateStr,                          // date
                            startTimeStr,                     // startTime
                            endTimeStr,                       // endTime
                            session.getRoomNumber(),          // place
                            session.getAvailableSeats(),       // availableSeats
                            session.getTotalSeats(),           // allSeats
                            session.getPrice()                 // price
                    );
                })
                .collect(Collectors.toList());
    }

}
