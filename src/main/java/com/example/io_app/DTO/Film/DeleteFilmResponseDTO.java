package com.example.io_app.DTO.Film;

public class DeleteFilmResponseDTO {

    private boolean result;

    public DeleteFilmResponseDTO(boolean result){
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
