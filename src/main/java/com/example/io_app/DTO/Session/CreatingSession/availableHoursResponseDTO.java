package com.example.io_app.DTO.Session.CreatingSession;

import java.time.LocalTime;
import java.util.List;

public class availableHoursResponseDTO {
    private List<LocalTime> openTimeSlots;

    public availableHoursResponseDTO(List<LocalTime> openTimeSlots) {
        this.openTimeSlots = openTimeSlots;
    }

    public List<LocalTime> getOpenTimeSlots() {
        return openTimeSlots;
    }

    public void setOpenTimeSlots(List<LocalTime> openTimeSlots) {
        this.openTimeSlots = openTimeSlots;
    }
}
