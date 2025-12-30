package gr.hua.dit.fittrack.core.service;

import gr.hua.dit.fittrack.core.port.impl.dto.TrainerPublicView;

import java.util.List;

public interface TrainerPublicService {

    /**
     * Public listing of trainers (for guests)
     */
    List<TrainerPublicView> listPublicTrainers();
}
