package gr.hua.dit.fittrack.web.ui;

import gr.hua.dit.fittrack.core.service.PersonDataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TrainerController {

    private final PersonDataService personDataService;

    public TrainerController(PersonDataService personDataService) {
        this.personDataService = personDataService;
    }

    @GetMapping("/trainers")
    public String showTrainers(Model model) {
        model.addAttribute("trainers", personDataService.getAllTrainers());
        return "trainers";
    }
}
