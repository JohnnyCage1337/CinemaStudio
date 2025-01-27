package com.example.io_app.DTO.Session.CreateSession;

import java.time.LocalDate;

public class availableTimeSlotsDueDateRequestDTO {
    LocalDate dueDate;

    public availableTimeSlotsDueDateRequestDTO(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}
