package com.example.io_app.DTO.Session;

public class DeleteSessionResponseDTO {

    private boolean result;

    public DeleteSessionResponseDTO(boolean result){
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
