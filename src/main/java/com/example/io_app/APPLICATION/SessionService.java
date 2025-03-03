package com.example.io_app.APPLICATION;

import com.example.io_app.DOMAIN.Film.Film;
import com.example.io_app.DTO.Session.CreateSession.CreateSessionRequestDTO;
import com.example.io_app.DTO.Session.CreateSession.CreateSessionResponseDTO;
import com.example.io_app.DTO.Session.CreateSession.availableHoursResponseDTO;
import com.example.io_app.DOMAIN.Session.Session;
import com.example.io_app.DOMAIN.Session.SessionManager;
import com.example.io_app.DTO.Session.*;
import com.example.io_app.DTO.Session.CreateSession.availableTimeSlotsDueDateRequestDTO;
import com.example.io_app.DTO.Session.DeleteSession.DeleteSessionRequestDTO;
import com.example.io_app.DTO.Session.DeleteSession.DeleteSessionResponseDTO;
import com.example.io_app.DTO.Session.FindSession.FindSessionByFilmTitleRequestDTO;
import com.example.io_app.DTO.Session.FindSession.FindSessionByFilmTitleResponseDTO;
import com.example.io_app.DTO.Session.UpdateSession.UpdateSessionRequestDTO;
import com.example.io_app.INFRASTRUCTURE.FilmRepository;
import com.example.io_app.INFRASTRUCTURE.SessionRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SessionService {

    private final SessionRepository sessionRepository;
    private final FilmRepository filmRepository;

    public SessionService() {
        this.sessionRepository = new SessionRepository();
        this.filmRepository = new FilmRepository();
    }

    public CreateSessionResponseDTO createSessionUseCase(CreateSessionRequestDTO requestDTO) {

        SessionManager sessionManager = new SessionManager();
        Session session = sessionManager.createSession(
                requestDTO.getFilmID(),
                requestDTO.getDate(),
                requestDTO.getStartTime(),
                requestDTO.getStartTime().plusMinutes(filmRepository.findByID(requestDTO.getFilmID()).getDuration()),
                requestDTO.getRoomNumber(),
                requestDTO.getTotalSeats(),
                requestDTO.getPrice()
        );

        sessionRepository.save(session);
        return new CreateSessionResponseDTO(session.getId());
    }

    public DeleteSessionResponseDTO deleteSessionUseCase(DeleteSessionRequestDTO requestDTO){
        return new DeleteSessionResponseDTO(sessionRepository.deleteByID(requestDTO.getSessionDTO().getSessionId()));
    }

    public List<SessionDTO> getAllSessions() {
        return sessionRepository.findAll()
                .stream()
                .map(session -> {
                    // Zamień LocalDate -> String "yyyy-MM-dd"
                    String dateStr = session.getDate() != null
                            ? session.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                            : "";

                    // Zamień LocalTime -> String "HH:mm"
                    String startTimeStr = session.getStartTime() != null
                            ? session.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm"))
                            : "";

                    String endTimeStr = session.getEndTime() != null
                            ? session.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm"))
                            : "";

                    return new SessionDTO(
                            session.getId(),
                            filmRepository.findByID(session.getFilmID()).getTitle(),
                            session.getDate(),
                            session.getStartTime(),
                            session.getEndTime(),
                            session.getRoomNumber(),
                            session.getAvailableSeats(),
                            session.getTotalSeats(),
                            session.getPrice()
                    );
                })
                .collect(Collectors.toList());
    }

    public FindSessionByFilmTitleResponseDTO findSessionByTitleUseCase(FindSessionByFilmTitleRequestDTO requestDTO) {

        //lista filmów znalezionych po tytule z przekazanego fragmentu
        List<Film> foundFilms = filmRepository.findByTitle(requestDTO.getFilmTitleFragment());

        //utworzenie listy seansów
        List<Session> foundSessions = new ArrayList<>();

        //dodanie seansów do listy na podstawie ID filmów
        for (Film film : foundFilms) {
            // Pobierz listę sesji dla danego filmu
            List<Session> sessions = sessionRepository.findByFilmID(film.getId());

            // Sprawdź, czy lista sesji nie jest pusta
            if (sessions != null && !sessions.isEmpty()) {
                // Dodaj wszystkie sesje do foundSessions
                foundSessions.addAll(sessions);
            }
        }

        //zmapowanie List<Sessions> do List<SessionDTO>
        List<SessionDTO> mappedFoundSessions = foundSessions.stream()
                .map(session -> {
                    return new SessionDTO(
                            session.getId(),
                            filmRepository.findByID(session.getFilmID()).getTitle(),
                            session.getDate(),
                            session.getStartTime(),
                            session.getEndTime(),
                            session.getRoomNumber(),
                            session.getAvailableSeats(),
                            session.getTotalSeats(),
                            session.getPrice()
                    );
                })
                .collect(Collectors.toList());

        //tworzenie responseDTO
        FindSessionByFilmTitleResponseDTO responseDTO = new FindSessionByFilmTitleResponseDTO(mappedFoundSessions);

        return responseDTO;
    }


    public List<SessionDTO> getSessionByDate(LocalDate date) {
        return sessionRepository.findSessionsByDate(date)
                .stream()
                .map(session -> {
                    // Zamień LocalDate -> String "yyyy-MM-dd"
                    String dateStr = session.getDate() != null
                            ? session.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                            : "";

                    // Zamień LocalTime -> String "HH:mm"
                    String startTimeStr = session.getStartTime() != null
                            ? session.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm"))
                            : "";

                    String endTimeStr = session.getEndTime() != null
                            ? session.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm"))
                            : "";

                    return new SessionDTO(
                            session.getId(),
                            filmRepository.findByID(session.getFilmID()).getTitle(),
                            session.getDate(),
                            session.getStartTime(),
                            session.getEndTime(),
                            session.getRoomNumber(),
                            session.getAvailableSeats(),
                            session.getTotalSeats(),
                            session.getPrice()
                    );
                })
                .collect(Collectors.toList());
    }

    public availableHoursResponseDTO getAvailableTimeSlotsDueDate(
            availableTimeSlotsDueDateRequestDTO requestDto,
            int roomNumber
    ) {
        List<Session> allSessionsForDate = sessionRepository.findSessionsByDate(requestDto.getDueDate());

        List<Session> sessions = allSessionsForDate.stream()
                .filter(session -> session.getRoomNumber() == roomNumber)
                .collect(Collectors.toList());

        sessions.sort(Comparator.comparing(Session::getStartTime));

        LocalTime dayStart = LocalTime.of(10, 0);
        LocalTime dayEnd = isWeekend(requestDto.getDueDate()) ?
                LocalTime.of(23, 0) : LocalTime.of(20, 0);

        List<LocalTime> freeTimeSlots = new ArrayList<>();
        LocalTime current = dayStart;

        while (!current.isAfter(dayEnd.minusMinutes(15))) {
            LocalTime next = current.plusMinutes(15);

            boolean conflicts = false;
            for (Session session : sessions) {
                if (isOverlapping(current, next, session.getStartTime(), session.getEndTime())) {
                    conflicts = true;
                    break;
                }
            }

            if (!conflicts) {
                freeTimeSlots.add(current);
            }
            current = next;
        }

        return new availableHoursResponseDTO(freeTimeSlots);
    }

    /**
     * Metoda pomocnicza do sprawdzenia, czy dwa przedziały czasowe nachodzą na siebie.
     * Tutaj: [startA, endA) oraz [startB, endB).
     */
    private boolean isOverlapping(LocalTime startA, LocalTime endA,
                                  LocalTime startB, LocalTime endB) {
        // Dwa przedziały zachodzą na siebie, jeśli początek jednego jest wcześniejszy niż koniec drugiego
        // oraz koniec pierwszego jest późniejszy niż początek drugiego.
        return !startA.isAfter(endB) && !endA.isBefore(startB);
    }


    private  boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY || DayOfWeek.FRIDAY == dayOfWeek;
    }

    public void updateSessionUseCase(UpdateSessionRequestDTO request) {
        var session = sessionRepository.findById(request.getSessionId());
        if(session == null) {
            throw new RuntimeException("Film not found");
        }

        session.setFilmID(request.getFilmID());
        session.setDate(request.getDate());
        session.setStartTime(request.getStartTime());
        session.setEndTime(request.getEndTime());
        session.setRoomNumber(request.getRoomNumber());
        session.setTotalSeats(request.getTotalSeats());
        session.setPrice(request.getPrice());

        sessionRepository.update(session);
    }

}
