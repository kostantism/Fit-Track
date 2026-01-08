package gr.hua.dit.fittrack.web.rest;

import gr.hua.dit.fittrack.core.service.AvailabilityService;
import gr.hua.dit.fittrack.core.service.model.AvailabilitySlot;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/availability")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @GetMapping("/slots")
    public List<AvailabilitySlot> getSlots(
            @RequestParam Long trainerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date
    ) {
        return availabilityService.getAvailableSlots(trainerId, date);
    }
}
