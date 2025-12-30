package gr.hua.dit.fittrack.core.service;

import gr.hua.dit.fittrack.core.model.Person;
import gr.hua.dit.fittrack.core.model.TrainerAvailability;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface AvailabilityService {

    TrainerAvailability createAvailability(Person trainer, LocalDate date, LocalDateTime startTime, LocalDateTime endTime);

}
