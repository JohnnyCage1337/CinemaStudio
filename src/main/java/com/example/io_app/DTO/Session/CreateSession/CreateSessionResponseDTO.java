package com.example.io_app.DTO.Session.CreateSession;

public class CreateSessionResponseDTO {
    int sessionId;

    public CreateSessionResponseDTO(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getSessionId() {
        return sessionId;
    }
}
