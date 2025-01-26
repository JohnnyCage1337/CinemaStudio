package com.example.io_app.DTO.Session.CreatingSession;

import java.time.LocalDate;
import java.time.LocalTime;

public class availableTimeSlotsDueDateRequestDTO {
    LocalDate dueDate;

    public availableTimeSlotsDueDateRequestDTO(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}
