package com.example.io_app.DTO.Session.FindSession;

import com.example.io_app.DOMAIN.Session.Session;
import com.example.io_app.DTO.Session.SessionDTO;

import java.util.List;

public class FindSessionByFilmTitleResponseDTO {

    private List<SessionDTO> foundSessions;

    public FindSessionByFilmTitleResponseDTO(List<SessionDTO> foundSessions) {
        this.foundSessions = foundSessions;
    }

    public List<SessionDTO> getFoundSessions() {
        return foundSessions;
    }
}