package gr.hua.dit.fittrack.web.ui.customer;

import gr.hua.dit.fittrack.core.port.WeatherPort;
import gr.hua.dit.fittrack.core.port.impl.dto.WeatherInfo;
import gr.hua.dit.fittrack.core.security.CurrentUserProvider;
import gr.hua.dit.fittrack.core.service.AppointmentService;
import gr.hua.dit.fittrack.core.service.AvailabilityService;
import gr.hua.dit.fittrack.core.service.PersonDataService;
import gr.hua.dit.fittrack.core.service.model.AvailabilitySlot;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/customer/book")
@PreAuthorize("hasRole('CUSTOMER')")
public class CustomerBookingController {

    private final AppointmentService appointmentService;
    private final PersonDataService personDataService;
    private final CurrentUserProvider currentUser;
    private final WeatherPort weatherPort;
    private final AvailabilityService availabilityService;

    public CustomerBookingController(
            AppointmentService appointmentService,
            PersonDataService personDataService,
            CurrentUserProvider currentUser,
            WeatherPort weatherPort,
            AvailabilityService availabilityService
    ) {
        this.appointmentService = appointmentService;
        this.personDataService = personDataService;
        this.currentUser = currentUser;
        this.weatherPort = weatherPort;
        this.availabilityService = availabilityService;
    }

//    @GetMapping
//    public String bookForm(
//            @RequestParam(required = false) LocalDate date,
//            Model model) {
//
//        model.addAttribute("trainers", personDataService.getAllTrainers());
//
//        if (date != null) {
//            WeatherInfo weather = weatherPort.getForecast(
//                    "Athens",
//                    date.atStartOfDay()
//            );
//            model.addAttribute("weather", weather);
//        }
//
//        return "customer/book";
//    }

//    @GetMapping
//    public String bookForm(
//            @RequestParam(required = false) Long trainerId,
//            @RequestParam(required = false) LocalDate date,
//            Model model) {
//
//        model.addAttribute("trainers", personDataService.getAllTrainers());
//        model.addAttribute("selectedTrainerId", trainerId);
//        model.addAttribute("selectedDate", date);
//
//        if (trainerId != null && date != null) {
//            List<AvailabilitySlot> availabilities =
//                    availabilityService.getAvailableSlots(trainerId, date);
//            model.addAttribute("availabilities", availabilities);
//        }
//
//        if (date != null) {
//            WeatherInfo weather =
//                    weatherPort.getForecast("Athens", date.atStartOfDay());
//            model.addAttribute("weather", weather);
//        }
//
//        return "customer/book";
//    }

//    @GetMapping
//    public String bookForm(
//            @RequestParam(required = false) Long trainerId,
//            @RequestParam(required = false)
//            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//            LocalDate date,
//            Model model
//    ) {
//
//        model.addAttribute("trainers", personDataService.getAllTrainers());
//
//        if (trainerId != null && date != null) {
//            List<AvailabilitySlot> slots =
//                    availabilityService.getAvailableSlots(trainerId, date);
//
//            model.addAttribute("availabilities", slots);
//            model.addAttribute("selectedTrainerId", trainerId);
//            model.addAttribute("selectedDate", date);
//        }
//
//        return "customer/book";
//    }
//
//    @PostMapping
//    public String submitBooking(
//            @RequestParam Long trainerId,
//            @RequestParam String startDateTime,
//            @RequestParam String endDateTime
//    ) {
//
//        Long customerId = currentUser.requireCustomerId();
//
//        appointmentService.createAppointment(
//                customerId,
//                trainerId,
//                LocalDateTime.parse(startDateTime),
//                LocalDateTime.parse(endDateTime)
//        );
//
//        return "redirect:/customer/book";
//    }
//}


    @GetMapping
    public String bookForm(
            @RequestParam(required = false) Long trainerId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date,
            Model model
    ) {

        // Πάντα trainers
        model.addAttribute("trainers", personDataService.getAllTrainers());

        // ================= AVAILABILITY =================
        if (trainerId != null && date != null) {
            List<AvailabilitySlot> slots =
                    availabilityService.getAvailableSlots(trainerId, date);

            model.addAttribute("availabilities", slots);
            model.addAttribute("selectedTrainerId", trainerId);
            model.addAttribute("selectedDate", date);
        }

        // ================= WEATHER =================
        if (date != null) {
            WeatherInfo weather =
                    weatherPort.getForecast("Athens", date.atStartOfDay());
            model.addAttribute("weather", weather);
        }

        return "customer/book";
    }

    @PostMapping
    public String submitBooking(
            @RequestParam Long trainerId,
            @RequestParam String startDateTime,
            @RequestParam String endDateTime
    ) {

        Long customerId = currentUser.requireCustomerId();

        appointmentService.createAppointment(
                customerId,
                trainerId,
                LocalDateTime.parse(startDateTime),
                LocalDateTime.parse(endDateTime)
        );

        return "redirect:/customer/book";
    }
}


