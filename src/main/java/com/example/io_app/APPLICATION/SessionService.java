package com.example.io_app.APPLICATION;

import com.example.io_app.DOMAIN.Session.Session;
import com.example.io_app.DOMAIN.Session.SessionManager;
import com.example.io_app.DTO.Session.CreateSessionRequestDTO;
import com.example.io_app.DTO.Session.DeleteSessionRequestDTO;
import com.example.io_app.DTO.Session.DeleteSessionResponseDTO;
import com.example.io_app.DTO.Session.SessionDTO;
import com.example.io_app.INFRASTRUCTURE.FilmRepository;
import com.example.io_app.INFRASTRUCTURE.SessionRepository;

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
    }

    public DeleteSessionResponseDTO deleteSessionUseCase(DeleteSessionRequestDTO requestDTO){
        return new DeleteSessionResponseDTO(sessionRepository.deleteByID(requestDTO.getSessionDTO().getSessionID()));
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
                            dateStr,
                            startTimeStr,
                            endTimeStr,
                            session.getRoomNumber(),
                            session.getAvailableSeats(),
                            session.getTotalSeats(),
                            session.getPrice()
                    );
                })
                .collect(Collectors.toList());
    }


}
