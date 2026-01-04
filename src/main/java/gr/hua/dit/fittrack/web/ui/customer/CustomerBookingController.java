package gr.hua.dit.fittrack.web.ui.customer;

import gr.hua.dit.fittrack.core.model.Person;
import gr.hua.dit.fittrack.core.port.WeatherPort;
import gr.hua.dit.fittrack.core.port.impl.dto.WeatherInfo;
import gr.hua.dit.fittrack.core.service.AppointmentService;
import gr.hua.dit.fittrack.core.service.PersonDataService;
import gr.hua.dit.fittrack.core.security.CurrentUserProvider;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/customer/book")
@PreAuthorize("hasRole('CUSTOMER')")
public class CustomerBookingController {

    private final AppointmentService appointmentService;
    private final PersonDataService personDataService;
    private final CurrentUserProvider currentUser;
    private final WeatherPort weatherPort;

    public CustomerBookingController(
            AppointmentService appointmentService,
            PersonDataService personDataService,
            CurrentUserProvider currentUser,
            WeatherPort weatherPort) {

        this.appointmentService = appointmentService;
        this.personDataService = personDataService;
        this.currentUser = currentUser;
        this.weatherPort = weatherPort;
    }

    @GetMapping
    public String bookForm(
            @RequestParam(required = false) LocalDate date,
            Model model) {

        model.addAttribute("trainers", personDataService.getAllTrainers());

        if (date != null) {
            WeatherInfo weather = weatherPort.getForecast(
                    "Athens",
                    date.atStartOfDay()
            );
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

        // current logged-in customer
        Long customerId = currentUser.requireCustomerId();

        Person customer = personDataService.findPersonEntityById(customerId);
        Person trainer = personDataService.findPersonEntityById(trainerId);

        appointmentService.createAppointment(
                customer,
                trainer,
                LocalDateTime.parse(startDateTime),
                LocalDateTime.parse(endDateTime)
        );

        return "redirect:/customer/book";
    }
}

//package gr.hua.dit.fittrack.web.ui.customer;
//
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//@PreAuthorize("hasRole('CUSTOMER')")
//public class AppointmentBookingController {
//
//    @GetMapping("/appointments/book")
//    public String book() {
//        return "customer/book";
//    }
//}
