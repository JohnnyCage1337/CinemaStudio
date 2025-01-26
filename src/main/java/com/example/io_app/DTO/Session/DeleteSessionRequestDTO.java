package com.example.io_app.DTO.Session;

public class DeleteSessionRequestDTO {

    private SessionDTO sessionDTO;

    public DeleteSessionRequestDTO(SessionDTO sessionDTO) {
        this.sessionDTO = sessionDTO;
    }

    public SessionDTO getSessionDTO() {
        return sessionDTO;
    }

    public void setSessionDTO(SessionDTO sessionDTO) {
        this.sessionDTO = sessionDTO;
    }
}
