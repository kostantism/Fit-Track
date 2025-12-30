package gr.hua.dit.fittrack.web.rest;

import gr.hua.dit.fittrack.core.port.impl.dto.TrainerPublicView;
import gr.hua.dit.fittrack.core.service.TrainerPublicService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trainers")
public class PublicTrainerController {

    private final TrainerPublicService trainerPublicService;

    public PublicTrainerController(TrainerPublicService trainerPublicService) {
        this.trainerPublicService = trainerPublicService;
    }

    @GetMapping
    public List<TrainerPublicView> listTrainers() {
        return trainerPublicService.listPublicTrainers();
    }
}
